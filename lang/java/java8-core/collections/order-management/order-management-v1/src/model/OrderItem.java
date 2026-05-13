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

    // attributes
    private final String productId;
    private int quantity;
    private final double unitPrice;

    // constructor
    public OrderItem(String productId, int quantity, double unitPrice) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive");
        if (unitPrice <= 0)
            throw new IllegalArgumentException("Unit price cannot be negative");
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // getters
    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getLineTotal() {
        return unitPrice * quantity;
    }

    // setters
    /**
     * Mutating quantity us valid: a user changes quantity on the same line item.
     * But productId is final - you don't change what product this line refers to.
     * */
    public void setQuantity(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive");
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if(!(o instanceof OrderItem))
            return false;
        OrderItem that = (OrderItem) o;
        return Objects.equals(this.productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "OrderItem{productId='" + productId + "', qty=" + quantity +
                ", unitPrice=" + unitPrice + ", lineTotal=" + getLineTotal() + "}";
    }

}
