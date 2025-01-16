package models;

import jakarta.persistence.*;
import logic.Logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Created every time a client eats at the restaurant
 *
 */
@Entity
public class Ticket {
    public static int DEFAULT_TABLE_NUMBER = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "emitted", nullable = false)
    private boolean emitted;

    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "tableNumber", nullable = false)
    private int tableNumber;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<LiveProduct> liveProducts;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<LiveMenu> liveMenus;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ticket")
    private Statement statement;

    public List<LiveMenu> getLiveMenus() {
        return liveMenus;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public boolean isEmitted() {
        return emitted;
    }

    public void setEmitted(boolean emitted) {
        this.emitted = emitted;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public List<LiveProduct> getLiveProducts() {
        return liveProducts;
    }

    public Ticket(
            boolean emitted, Restaurant restaurant, Date date, int tableNumber, List<LiveProduct> liveProducts,
            List<LiveMenu> liveMenus
    ) {
        this.emitted = emitted;
        this.restaurant = restaurant;
        this.date = date;
        this.tableNumber = tableNumber;
        this.liveProducts = liveProducts;
        this.liveMenus = liveMenus;
    }

    public Ticket() {
        this.emitted = false;
        this.date = new Date();
        this.tableNumber = DEFAULT_TABLE_NUMBER;
        this.liveProducts = new ArrayList<>();
        this.liveMenus = new ArrayList<>();
    }

    public double getTotalCost() {
        return
                this.liveProducts.stream().map(LiveProduct::getCost).reduce(0.0, Double::sum)
                + this.liveMenus.stream().map(LiveMenu::getCost).reduce(0.0, Double::sum)
        ;
    }

    public double getTotalATICost() {
        return
                this.liveProducts.stream().map(LiveProduct::getATICost).reduce(0.0, Double::sum)
                + this.liveMenus.stream().map(LiveMenu::getATICost).reduce(0.0, Double::sum)
        ;
    }
}
