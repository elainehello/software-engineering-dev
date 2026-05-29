## `README.md`

```markdown
# distributed-lab

A local distributed systems lab built with Go, Kafka, Kong, and the Grafana observability stack. Everything runs in Docker Compose — no cloud account needed.

---

## Stack

| Layer | Technology | Purpose |
|---|---|---|
| Language | Go 1.22 | All services |
| Gateway | Kong 3.6 (DB-less) | Routing, JWT validation, rate limiting |
| Broker | Apache Kafka | Async event streaming |
| Database | PostgreSQL 16 | Persistent storage |
| Cache | Redis 7 | Idempotency keys, distributed locks |
| Metrics | Prometheus + Grafana | Dashboards, latency histograms |
| Tracing | OpenTelemetry + Tempo | Distributed trace propagation |
| Kafka UI | Provectus Kafka UI | Inspect topics and consumer groups |

---

## Architecture

```text
                        ┌─────────────────┐
    HTTP traffic  ───▶  │   Kong :8000    │  API Gateway
                        └────────┬────────┘
                                 │ routes + JWT validation
              ┌──────────────────┼──────────────────┐
              │                  │                  │
     ┌────────▼───────┐ ┌────────▼──────┐ ┌────────▼───────┐
     │ auth-service   │ │ order-service │ │product-service │
     │ :4001          │ │ :4004         │ │ :4003          │
     └────────────────┘ └───────┬───────┘ └────────────────┘
                                │
                    publishes order.created
                                │
                        ┌───────▼───────┐
                        │     Kafka     │  topic: order.created
                        └───────┬───────┘
                                │
                        ┌───────▼────────────┐
                        │ notification-svc   │  consumer group
                        │ :4005              │
                        └────────────────────┘

    Observability
    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
    │ Prometheus  │    │   Grafana   │    │    Tempo    │
    │ :9090       │───▶│ :3000       │    │ :3200       │
    └─────────────┘    └─────────────┘    └─────────────┘
         scrapes all services              receives OTLP traces
```

---

## Project Structure

```text
distributed-lab/
├── services/
│   ├── auth/               # Register, login, JWT issue + validate
│   │   ├── main.go
│   │   ├── handler.go
│   │   ├── middleware.go
│   │   └── Dockerfile
│   ├── user/               # User CRUD
│   ├── product/            # Product catalogue
│   ├── order/              # Order creation + Kafka producer
│   │   ├── main.go
│   │   ├── handler.go
│   │   ├── producer.go
│   │   └── Dockerfile
│   └── notification/       # Kafka consumer — sends confirmations
│       ├── main.go
│       ├── consumer.go
│       └── Dockerfile
├── shared/
│   ├── tracing/
│   │   └── otel.go         # OpenTelemetry init (imported by every service)
│   ├── middleware/
│   │   └── logging.go      # Structured JSON logs + HTTP middleware
│   └── events/
│       └── types.go        # Kafka event schemas (single source of truth)
├── infra/
│   ├── kong/kong.yml       # Declarative gateway config (DB-less)
│   ├── postgres/init.sql   # Schema + seed data
│   ├── prometheus/         # Scrape config
│   ├── tempo/              # Trace storage config
│   └── grafana/            # Auto-provisioned datasources
├── proto/
│   └── order.proto         # gRPC schemas (phase 4)
├── scripts/
│   ├── chaos.sh            # Kill / restore services
│   └── load.sh             # Load testing with hey
├── go.mod
└── docker-compose.yml
```

---

## Prerequisites

| Tool | Install |
|---|---|
| Go 1.22+ | https://go.dev/dl |
| Docker Desktop | https://www.docker.com/products/docker-desktop |
| hey (load testing) | `go install github.com/rakyll/hey@latest` |

VS Code extensions: **Go** (Google), **Docker** (Microsoft), **REST Client** (Humao).

---

## Getting Started

### 1. Clone and initialise

```bash
git clone <your-repo> distributed-lab
cd distributed-lab
go mod tidy
```

### 2. Start infrastructure first

Bring up stateful services and wait until they are healthy before starting application services. Kafka takes the longest (~30s).

```bash
docker compose up postgres redis zookeeper kafka -d
docker compose ps
```

All four should show `(healthy)` before continuing.

### 3. Start everything else

```bash
docker compose up --build -d
```

### 4. Verify

```bash
curl http://localhost:8000/health
docker compose ps
```

---

## Service Endpoints

All public traffic goes through Kong on port `8000`. Direct service ports are available for debugging only.

| Service | Kong path | Direct port |
|---|---|---|
| auth-service | `/auth/*` | `:4001` |
| user-service | `/users/*` | `:4002` |
| product-service | `/products/*` | `:4003` |
| order-service | `/orders/*` | `:4004` |
| notification-service | `/notification/health` | `:4005` |
| Kong admin | — | `:8001` |
| Kafka UI | — | `:8080` |
| Prometheus | — | `:9090` |
| Grafana | — | `:3000` |
| Tempo | — | `:3200` |

---

## First End-to-End Test

Run these in order. Each step depends on the previous one.

```bash
# 1. Register a user
curl -s -X POST http://localhost:8000/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"you@lab.dev","password":"test123","name":"You"}' | jq

# 2. Login and copy the token
curl -s -X POST http://localhost:8000/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"you@lab.dev","password":"test123"}' | jq

# 3. Create an order (paste your token)
curl -s -X POST http://localhost:8000/orders \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "product_id": "00000000-0000-0000-0000-000000000001",
    "quantity": 1,
    "total_price": 29.99
  }' | jq

# 4. Check notification-service received the Kafka event
docker logs lab-notification --tail 20

# 5. Inspect the event in Kafka UI
open http://localhost:8080
```

---

## Observability

### Grafana — http://localhost:3000

Login: `admin / admin`

Prometheus and Tempo are auto-provisioned on first boot. No manual setup needed.

Useful Prometheus queries in Explore:

```promql
# Request rate per service
rate(http_requests_total[1m])

# p99 latency
histogram_quantile(0.99, rate(http_request_duration_seconds_bucket[5m]))

# Error rate
rate(http_requests_total{status=~"5.."}[1m])
```

### Tempo traces

Go to Grafana → Explore → select the **Tempo** datasource. Search by service name or paste a trace ID directly from a log line.

Every log line includes a `trace_id` field:

```json
{
  "service": "order-service",
  "method": "POST",
  "path": "/orders",
  "status": 201,
  "trace_id": "4bf92f3577b34da6a3ce929d0e0e4736"
}
```

Paste that ID into Tempo to see the full request path across services:

```
Kong → order-service → Kafka → notification-service
```

---

## Load Testing

Requires `hey` — install with `go install github.com/rakyll/hey@latest`.

```bash
# Register 50 test users
./scripts/load.sh register

# Hammer order-service with 200 requests (paste a valid JWT)
./scripts/load.sh orders <TOKEN>

# Trigger rate limiting on product listing
./scripts/load.sh ratelimit
```

---

## Chaos Testing

```bash
# Kill a specific service
./scripts/chaos.sh kill product

# Kill a random service
./scripts/chaos.sh kill random

# Restore a service
./scripts/chaos.sh restore product

# Restore everything
./scripts/chaos.sh restore all

# Add 200ms network latency to order-service
./scripts/chaos.sh network-delay 200

# Show container status
./scripts/chaos.sh status
```

After killing a service, watch the error rate spike in Grafana. Restore the service and watch traffic resume automatically.

---

## Phases

The lab is designed in four progressive phases. Each one adds complexity on top of a working foundation.

### Phase 1 — Foundation

Go HTTP services, Docker Compose, PostgreSQL, JWT auth, Kong routing, structured logs.

**Goal:** `POST /orders` routes through Kong, hits order-service, persists to Postgres.

### Phase 2 — Events

Kafka producer in order-service, consumer in notification-service, Redis idempotency keys, at-least-once delivery, dead-letter topic.

**Goal:** creating an order publishes `order.created`, notification-service consumes it.

### Phase 3 — Observability

OpenTelemetry trace propagation across HTTP and Kafka boundaries, Tempo, Prometheus metrics, Grafana dashboards, latency histograms, p99 alerts.

**Goal:** one trace ID visible end-to-end from Kong to notification-service in Tempo.

### Phase 4 — Resilience

Circuit breakers, retry with exponential backoff, timeout propagation, gRPC inter-service calls, chaos drills, outbox pattern for guaranteed Kafka delivery.

**Goal:** kill product-service, watch circuit open in under 5s, restore and watch it recover.

---

## Useful Commands

```bash
# Rebuild and restart a single service after a code change
docker compose up --build auth-service -d

# Stream logs from all services
docker compose logs -f

# Stream logs from one service
docker logs lab-order -f

# Open a psql shell
docker exec -it lab-postgres psql -U lab -d lab

# Open a Redis CLI
docker exec -it lab-redis redis-cli

# List Kafka topics
docker exec lab-kafka kafka-topics --bootstrap-server localhost:9092 --list

# Read messages from a topic from the beginning
docker exec lab-kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic order.created \
  --from-beginning

# Reload Kong config without restarting the container
docker exec lab-kong kong reload

# Stop everything and remove volumes (full reset)
docker compose down -v
```

---

## Environment Variables

Every service reads configuration from environment variables defined in `docker-compose.yml`. For running a service locally outside Docker, create a `.env` file in the service directory.

| Variable | Used by | Description |
|---|---|---|
| `PORT` | all services | HTTP listen port |
| `POSTGRES_DSN` | auth, user, product, order | PostgreSQL connection string |
| `REDIS_ADDR` | auth, order, product | Redis address |
| `KAFKA_BROKERS` | order, notification | Comma-separated broker list |
| `JWT_SECRET` | auth | HMAC signing key — change in production |
| `SERVICE_NAME` | all services | Injected into logs and traces |
| `OTEL_EXPORTER_OTLP_ENDPOINT` | all services | Tempo OTLP HTTP endpoint |

---

## Resetting the Lab

```bash
# Stop containers, keep volumes (data preserved)
docker compose down

# Stop containers and delete all data (full reset)
docker compose down -v

# Rebuild all images from scratch
docker compose build --no-cache
docker compose up -d
```

---

## Notes

- Kong runs in **DB-less mode**. All config lives in `infra/kong/kong.yml`. Changes require `docker exec lab-kong kong reload`.
- Tempo retains traces for **1 hour** in dev. Increase `block_retention` in `infra/tempo/tempo.yml` if you need longer.
- The `JWT_SECRET` in `docker-compose.yml` is for local development only. Never commit a real secret.
- Scripts must be executable before first use: `chmod +x scripts/*.sh`
```