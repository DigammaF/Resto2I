package models;

import jakarta.persistence.*;
import logic.ProductType;
import logic.Tax;

/**
 *
 * Archetype of a consumable item
 *
 */
@Entity
public class Product {
    public static boolean DEFAULT_AVAILABLE = false;
    public static String DEFAULT_NAME = "unamed";
    public static double DEFAULT_COST = 0.0;
    public static Tax DEFAULT_TAX = Tax.INSTANT;
    public static ProductType DEFAULT_PRODUCT_TYPE = ProductType.DEFAULT;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    /**
     * Available to clients?
     */
    @Column(name = "available", nullable = false)
    private boolean available;

    /**
     * Available in the editor?
     */
    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "tax", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tax tax;

    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @Column(name = "product_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "tags", nullable = false)
    private String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Product(
            Restaurant restaurant,
            boolean available, String name, double cost, Tax tax, boolean used, ProductType productType,
            String tags
    ) {
        this.available = available;
        this.name = name;
        this.cost = cost;
        this.tax = tax;
        this.restaurant = restaurant;
        this.used = used;
        this.productType = productType;
        this.tags = tags;
    }

    public Product() {
        this.available = DEFAULT_AVAILABLE;
        this.name = DEFAULT_NAME;
        this.cost = DEFAULT_COST;
        this.tax = DEFAULT_TAX;
        this.used = true;
        this.productType = DEFAULT_PRODUCT_TYPE;
        this.tags = "";
    }

    public double getATICost() {
        return this.tax.applied(this.cost);
    }

    public String describeCosts() {
        return String.format("%s - %.2f€ - %.2f€", name, this.cost, this.getATICost());
    }

    public String toString() {
        return String.format("[%.2f€ %s TVA] %s", this.cost, this.tax.toString(), this.name);
    }
}
