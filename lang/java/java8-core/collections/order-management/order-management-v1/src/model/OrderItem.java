package model;

import java.util.Objects;

/**
 * A single lime item in an order.
 * Represents one product + quantity pair.
 *
 * Note:
 * equals() and hashCode() are based on productId only.
 * This allows OrderService to check:
 *  items.contains(new OrderItem("SKU-001", 1))
 *  and find the existing item - useful for deduplication logic
 *
 *  Alternatively, if you need full-value equality (productId + quantity),
 *  include quantity in both methods. The choice depends on the business logic you need to implement.
 *  Here: one line item per product - productId is identity.
 */

public class OrderItem {
}
