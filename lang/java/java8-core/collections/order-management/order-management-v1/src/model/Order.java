package model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a customer order.
 *
 * Note:
 * Order holds only identity and metadata.
 * The actual items and business logic live in OrderService.
 * This separation prevents Order from becoming a "God object" -
 * a commong mistake where the model bleeds into service logic.
 *
 * In a real backend, this maps cleanly to a database row.
 * The service layer manages the collection of items separately
 * (which would mao to an order_items join table).
 */
public class Order {
    // enum
    public enum Status {
        CREATED, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

    // attributes
    private final String id;
    private final String customerId;
    private Status status;
    private final LocalDateTime createdAt;

    // constructor
    public Order(String customerId) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.status = Status.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    // getters
    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // setter
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
