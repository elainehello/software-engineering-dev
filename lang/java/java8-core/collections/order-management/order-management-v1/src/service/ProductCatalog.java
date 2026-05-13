package service;

import model.Product;

import java.util.*;

/**
 * In-memory product catalog with O(1) lookup and an audit log.
 *
 * Collections used:
 *
 *   HashMap<String, Product>  — the main catalog
 *     Why: product lookup by id/SKU is the hottest path in any
 *          e-commerce backend. O(1) average get() is non-negotiable.
 *          Key is String (productId) — immutable, well-defined hashCode().
 *
 *   LinkedHashMap<String, String>  — audit log of catalog changes
 *     Why: we need insertion-order preservation for the audit trail.
 *          A regular HashMap would lose the sequence of events.
 *          LinkedHashMap maintains a doubly-linked list alongside
 *          the hash table — giving O(1) operations AND ordered iteration.
 *
 * HashMap internals you must know:
 *   - Default initial capacity: 16 buckets
 *   - Default load factor: 0.75
 *   - Resize (rehash) triggers when size > capacity * loadFactor
 *   - Rehashing is O(n) — expensive at runtime
 *   - If you know you'll store ~1000 products, pre-size:
 *     new HashMap<>(2000) — this avoids rehashing entirely
 *   - Since Java 8: buckets with 8+ collisions convert to a
 *     red-black tree, making worst-case O(log n) instead of O(n)
 */
public class ProductCatalog {
    // Does represent key-value
    // attributes
    private final Map<String, Product> catalog;
    private final Map<String, String> auditLog; // productId * event description

    // constructor

    public ProductCatalog() {
        // Pre-sizing: assume a catalog up to ~200 products.
        // Formula: expectedSize / loadFactor + 1 → 200 / 0.75 + 1 ≈ 268
        // We round up to a power of 2 for HashMap's internal bucket sizing.
        this.catalog = new HashMap<>(256);

        // LinkedHashMap: insertion order is preserved by default.
        // The 3rd constructor param (accessOrder=true) switches it to
        // access-order mode — the basis of an LRU cache. We keep default
        // (insertion order) here for a simple audit log.
        this.auditLog = new LinkedHashMap<>();
    }

    // methods
    /**
     * Adds or replaces a product in the catalog.
     *
     * Map.put() returns the previous value if the key existed, or null.
     * We use that return value to distinguish add vs update.
     */
    public void addProduct(Product product) {
        Product previous = catalog.put(product.getId(), product);

        if (previous == null) {
            auditLog.put(product.getId(), "ADDED: " + product.getName());
        } else {
            auditLog.put(product.getId(), "UPDATED: " + product.getName()
                    + " (was: " + previous.getName() + ")");
        }
    }

    /**
     * Lookup by product id.
     *
     * Note:
     * We return Optional<Product> instead of Product (which would force
     * callers to handle null). Optional is the Java 8 way of making
     * "this might not exist" explicit in the contract.
     *
     * Map.get() returns null for missing keys — wrapping with
     * Optional.ofNullable() converts that to an empty Optional cleanly.
     */
    public Optional<Product> findbyId(String productId) {
        return Optional.ofNullable(catalog.get(productId));
    }

    /**
     * getOrDefault() — introduced in Java 8 on Map.
     * Eliminates the common null-check boilerplate:
     *   Product p = catalog.get(id);
     *   if (p == null) p = fallback;
     * This is a single O(1) call.
     */
    public Product findByOrDefault(String productId, Product fallback) {
        return catalog.getOrDefault(productId, fallback);
    }

    /**
     * putIfAbsent() — another Java 8 Map addition.
     * Only inserts if the key is not already present.
     * Returns the existing value if key exists, null if it was inserted.
     * Useful for initialization patterns and cache population.
     */
    public boolean registerIfAbsent(Product product) {
        Product existing = catalog.putIfAbsent(product.getId(), product);
        boolean wasAbsent = (existing == null);

        if (wasAbsent) {
            auditLog.put(product.getId(), "REGISTERED: " + product.getName());
        }
        return wasAbsent;
    }

    public boolean removeProduct(String productId) {
        Product removed = catalog.remove(productId);
        if (removed != null) {
            auditLog.put(productId, "REMOVED: " + removed.getName());
            return true;
        }
        return false;
    }

    public boolean contains(String productId) {
        return catalog.containsKey(productId);
    }

    public int size() {
        return catalog.size();
    }

    /**
     * Unmodifiable view of the full catalog.
     * Callers can iterate but cannot mutate.
     */
    public Map<String, Product> getCatalog() {
        return Collections.unmodifiableMap(catalog);
    }

    /**
     * Audit log in insertion order — LinkedHashMap's guarantee.
     * Iterating this map gives events in the order they happened.
     */
    public Map<String, String> getAuditLog() {
        return Collections.unmodifiableMap(auditLog);
    }

}
