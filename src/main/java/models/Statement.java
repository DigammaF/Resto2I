package models;

import jakarta.persistence.*;

import java.util.Date;

/**
 *
 * Comes with a Ticket, destined to be read and used by the client
 *
 */
@Entity
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "due", nullable = false)
    private Date due;

    @Column(name = "latePenalty", nullable = false)
    private String latePenalty;

    @OneToOne(cascade = CascadeType.ALL)
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public String getLatePenalty() {
        return latePenalty;
    }

    public void setLatePenalty(String latePenalty) {
        this.latePenalty = latePenalty;
    }

    public Statement(Restaurant restaurant, Client client, double amount, Date due, String latePenalty) {
        this.restaurant = restaurant;
        this.client = client;
        this.amount = amount;
        this.due = due;
        this.latePenalty = latePenalty;
    }

    public Statement() {
    }
}
