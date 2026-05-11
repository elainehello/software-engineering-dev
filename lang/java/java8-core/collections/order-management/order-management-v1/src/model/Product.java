package model;

import java.util.Objects;

/**
 * Represents a product in the catalog
 *
 * Note:
 * We implement equals() and hashCode() based on id alone.
 * This is intentional - we consider two products with the same id to be the same product,
 * even if their name or price differ.
 * This is a common pattern for entities that have a unique identifier.
 *
 * This contract is required for correctness when Product is used as a HashMap key
 * and stored in a HashSet.
 * Violating it causes silent, catastrophic bugs:
 * - HashMap lookups return null even though the key is "exists"
 * - HashSet allows duplicates silently
 * */
public class Product {

    // attributes
    private final String id;    // e.g. "SKU-001"
    private final String name;
    private final double price;

    // constructor
    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Identity is based on id only.
     * Two products with the same id are the same product.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product that = (Product) o;
        return Objects.equals(this.id, that.id);
    }


    /**
     * Must be consistent with equals().
     * Rule: if a.equals(b) then a.hashCode() == b.hashCode()
     * Violation breaks all hash-based collections.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + "', price=" + price +"}";
    }
}
