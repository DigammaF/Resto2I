package models;

import jakarta.persistence.*;
import logic.ProductType;

@Entity
public class LiveProduct {
    /*

        A concrete realisation of a product archetype

     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @Column(name = "count", nullable = false)
    private int count;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ticket ticket;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public LiveProduct(Product product, int count, Ticket ticket) {
        this.product = product;
        this.count = count;
        this.ticket = ticket;
    }

    public LiveProduct() {

    }

    public double getCost() {
        return this.product.getCost() * this.count;
    }

    public double getATICost() {
        return this.product.getATICost() * this.count;
    }

    public ProductType getProductType() { return this.product.getProductType(); }
}
