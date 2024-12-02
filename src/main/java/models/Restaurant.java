package models;

import jakarta.persistence.*;
import logic.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Client> clients;

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

    public String getTaxID() {
        return tax_id;
    }

    public void setTaxID(String tax_id) {
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

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Restaurant(String name, String email, String address, String phone,
                      String tax_id, String SIREN, String latePenaltyPolicy,
                      List<Product> products, List<Ticket> tickets, List<Statement> statements,
                      List<Client> clients
    ) {
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
        this.clients = clients;
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
        this.clients = new ArrayList<Client>();
    }

    public Optional<Client> createClient() {
        return Optional.of(new Client());
    }

    public Optional<LiveProduct> createLiveProduct(Ticket ticket, List<Product> availableProducts) {
        if (availableProducts.isEmpty()) { return Optional.empty(); }
        else {
            LiveProduct liveProduct = new LiveProduct();
            liveProduct.setCount(1);
            liveProduct.setProduct(availableProducts.getFirst());
            Logic.addLiveProduct(ticket, liveProduct);
            return Optional.of(liveProduct);
        }
    }

    public Optional<Product> createProduct() {
        Product product = new Product();
        Logic.addProduct(this, product);
        return Optional.of(product);
    }

    public boolean initTicketStatement(Ticket ticket, Statement statement) {
        Logic.addTicket(this, ticket);
        Logic.addStatement(this, statement);
        Logic.bindTicketStatement(ticket, statement);
        Optional<Client> client = createClient();
        if (client.isPresent()) { statement.setClient(client.get()); }
        else { return false; }
        return true;
    }
}
