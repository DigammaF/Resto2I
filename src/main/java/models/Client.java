package models;

import jakarta.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "tax_id", nullable = false, length = 100)
    private String tax_id;

    @Column(name = "contact", nullable = false, length = 1000)
    private String contact;

    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    public Client(String name, String tax_id, String contact, Restaurant restaurant) {
        this.name = name;
        this.tax_id = tax_id;
        this.contact = contact;
        this.restaurant = restaurant;
    }

    public Client() {
        this.name = "name";
        this.tax_id = "tax_id";
        this.contact = "contact";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxID() {
        return tax_id;
    }

    public void setTaxID(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
