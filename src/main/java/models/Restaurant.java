package models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "phone", nullable = false, length = 100)
    private String phone;

    @Column(name = "tax_id", nullable = false, length = 100)
    private String tax_id;

    @Column(name = "SIREN", nullable = false, length = 100)
    private String SIREN;

    @Column(name = "latePenaltyPolicy", nullable = false, length = 100)
    private String latePenaltyPolicy;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Statement> statements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getSIREN() {
        return SIREN;
    }

    public void setSIREN(String SIREN) {
        this.SIREN = SIREN;
    }

    public String getLatePenaltyPolicy() {
        return latePenaltyPolicy;
    }

    public void setLatePenaltyPolicy(String latePenaltyPolicy) {
        this.latePenaltyPolicy = latePenaltyPolicy;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public Restaurant(String name, String email, String address, String phone, String tax_id, String SIREN, String latePenaltyPolicy, List<Product> products, List<Ticket> tickets, List<Statement> statements) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.tax_id = tax_id;
        this.SIREN = SIREN;
        this.latePenaltyPolicy = latePenaltyPolicy;
        this.products = products;
        this.tickets = tickets;
        this.statements = statements;
    }

    public Restaurant() {
        this.name = "unamed";
        this.email = "email";
        this.address = "address";
        this.phone = "phone";
        this.tax_id = "tax_id";
        this.SIREN = "SIREN";
        this.latePenaltyPolicy = "latePenaltyPolicy";
        this.products = new ArrayList<Product>();
        this.tickets = new ArrayList<Ticket>();
        this.statements = new ArrayList<Statement>();
    }
}
