package models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  A group of MenuItem s
 *
 */
@Entity
public class Menu {
    public static final String DEFAULT_NAME = "unnamed";
    public static final double DEFAULT_COST = 0.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @Column(name = "available")
    private boolean available;

    @Column(name = "cost")
    private double cost;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;

    @Column(name = "used")
    private boolean used;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu() {
        this.name = DEFAULT_NAME;
        this.cost = DEFAULT_COST;
        this.menuItems = new ArrayList<>();
        this.used = true;
    }
}
