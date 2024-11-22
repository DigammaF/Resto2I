package models;

import jakarta.persistence.*;
import logic.Logic;

import java.util.Date;
import java.util.List;

@Entity
public class Ticket {
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ticket")
    private Statement statement;

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

    public void setLiveProducts(List<LiveProduct> liveProducts) {
        this.liveProducts = liveProducts;
    }

    public Ticket(boolean emitted, Restaurant restaurant, Date date, int tableNumber, List<LiveProduct> liveProducts) {
        this.emitted = emitted;
        this.restaurant = restaurant;
        this.date = date;
        this.tableNumber = tableNumber;
        this.liveProducts = liveProducts;
    }

    public Ticket() {
    }

    public double getTotalCost() {
        return this.liveProducts.stream().map(LiveProduct::getCost).reduce(0.0, Double::sum);
    }

    public double getTotalATICost() {
        return this.liveProducts.stream().map(LiveProduct::getATICost).reduce(0.0, Double::sum);
    }

    public Statement emitStatement() {
        Statement statement = new Statement();
        statement.setRestaurant(this.getRestaurant());
        Logic.updateStatementAmount(this, statement);
        statement.setDue(this.getDate());
        statement.setLatePenalty(this.getRestaurant().getLatePenaltyPolicy());
        statement.setTicket(this);
        return statement;
    }
}
