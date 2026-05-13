package service;


import model.Order;
import model.OrderItem;

import java.util.*;

/**
 * Manages the lifecycle of a single order's line items.
 *
 * Collections used:
 *
 *   ArrayList<OrderItem>  — line items
 *     Why: items are accessed by index (display, pagination),
 *          appended frequently, rarely removed by index.
 *          ArrayList gives O(1) indexed access and O(1) amortized append.
 *          LinkedList would cost O(n) for indexed access — wrong choice here.
 *
 *   HashSet<String>  — applied discount codes
 *     Why: we need O(1) membership test ("has this code been used?")
 *          and silent deduplication. A List.contains() would be O(n).
 *          For a handful of codes that might seem fine, but it's the
 *          wrong data structure choice and shows in interviews.
 */
public class OrderService {

    // attributes
    private final Order order;

    // ArrayList: ordered, indexed, allows duplicates (we control dedup ourselves)
    private final List<OrderItem> items;

    // HashSet: O(1) contains, automatic deduplication, no ordering needed
    private final Set<String> appliedDiscountCodes;

    // constructor
    public OrderService(Order order) {
        this.order = order;
        this.items = new ArrayList<>();
        this.appliedDiscountCodes = new HashSet<>();
    }

    /**
     * Adds an item to the order, or increases quantity if already present.
     *
     * Note:
     * We use indexOf() here which is O(n) — acceptable because cart sizes
     * are small (typically < 50 items). For large datasets, you'd use a
     * HashMap<String, OrderItem> keyed by productId instead, giving O(1)
     * lookup. That's a real trade-off you'd discuss in a design interview.
     */
    public void addItem(OrderItem item) {
        int existingIndex = items.indexOf(item); // uses OrderItem.equals() -> compared productId

        if (existingIndex >= 0) {
            // Item already exists: increase quantity instead of adding duplicate line
            OrderItem existing = items.get(existingIndex);
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
    }

    /**
     * Removes an item by productId.
     *
     * Note:
     * removeIf() is available since Java 8. It iterates internally and
     * removes matching elements safely — avoids ConcurrentModificationException
     * that a naive for-loop with remove() would throw.
     */
    public boolean removeItem(String productId) {
        return items.removeIf(item -> item.getProductId().equals(productId));
    }

    /**
     * Applies a discount code to the order.
     * Returns false if the code was already applied (idempotent, no exception).
     *
     * Note:
     * Set.add() returns false if the element already existed.
     * This is cleaner than calling contains() first then add() —
     * that would be two hash lookups instead of one.
     */
    public boolean applyDiscountCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Discount code cannot be null or empty");
        }
        return appliedDiscountCodes.add(code.toUpperCase()); // normalise in entry
    }

    /**
     * Returns the total price of all line items.
     *
     * Pre-streams imperative style — we'll rewrite this with streams later.
     * Keeping it imperative here is intentional: you should be able to
     * read and write both styles fluently.
     */
    public double calculateTotal() {
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.getLineTotal();
        }
        return total;
    }

    /**
     * Returns an unmodifiable view of the item list.
     *
     * Note — this is critical defensive programming:
     * If we returned the raw ArrayList, any caller could do:
     *   orderService.getItems().clear();
     * and silently destroy the order's state.
     *
     * Collections.unmodifiableList() wraps the list — mutations throw
     * UnsupportedOperationException at runtime. The underlying list
     * is still the live view, so it reflects any changes made through
     * OrderService's own methods.
     *
     * In Java 9+ you'd use List.copyOf() for a true immutable snapshot.
     * In Java 8, this is the standard pattern.
     */
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Set<String> getAppliedDiscountCodes() {
        return Collections.unmodifiableSet(appliedDiscountCodes);
    }

    public Order getOrder() {
        return order;
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}
