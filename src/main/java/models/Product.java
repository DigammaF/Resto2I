package models;

import jakarta.persistence.*;
import logic.ProductType;
import logic.Tax;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @Column(name = "available", nullable = false)
    private boolean available;

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

    public Product(boolean available, String name, double cost, Tax tax, Restaurant restaurant, boolean used) {
        this.available = available;
        this.name = name;
        this.cost = cost;
        this.tax = tax;
        this.restaurant = restaurant;
        this.used = used;
    }

    public Product() {
        this.available = false;
        this.name = "unamed";
        this.cost = 0;
        this.tax = Tax.INSTANT;
        this.used = true;
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
