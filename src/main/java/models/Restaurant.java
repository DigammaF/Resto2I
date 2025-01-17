package models;

import jakarta.persistence.*;
import logic.Generation;
import logic.Logic;
import logic.ProductType;
import logic.Tax;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menu> menus;

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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public Restaurant(String name, String email, String address, String phone,
                      String tax_id, String SIREN, String latePenaltyPolicy,
                      List<Product> products, List<Ticket> tickets, List<Statement> statements,
                      List<Client> clients, List<Menu> menus
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
        this.menus = menus;
    }

    public Restaurant() {
        this.name = "unamed";
        this.email = "email";
        this.address = "address";
        this.phone = "phone";
        this.tax_id = "tax_id";
        this.SIREN = "SIREN";
        this.latePenaltyPolicy = "latePenaltyPolicy";
        this.products = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.statements = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.menus = new ArrayList<>();
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

    public Optional<LiveMenu> createLiveMenu(Ticket ticket, Menu menu) {
        LiveMenu liveMenu = new LiveMenu();
        liveMenu.setMenu(menu);
        Logic.addLiveMenu(ticket, liveMenu);
        for (MenuItem menuItem : menu.getMenuItems()) {
            List<Product> availableProducts = this.products.stream().filter(menuItem::allowed).toList();
            if (availableProducts.isEmpty()) { return Optional.empty(); }
            LiveMenuItem liveMenuItem = new LiveMenuItem();
            liveMenuItem.setLiveMenu(liveMenu);
            liveMenuItem.setAllowedTags(menuItem.getAllowedTags());
            liveMenuItem.setName(menuItem.getName());
            LiveProduct liveProduct = new LiveProduct();
            liveProduct.setCount(1);
            liveProduct.setProduct(availableProducts.getFirst());
            liveMenuItem.setLiveProduct(liveProduct);
            Logic.addLiveMenuItem(liveMenu, liveMenuItem);
        }
        return Optional.of(liveMenu);
    }

    public Optional<Menu> createMenu() {
        Menu menu = new Menu();
        Logic.addMenu(this, menu);
        return Optional.of(menu);
    }

    public Optional<MenuItem> createMenuItem(Menu menu) {
        MenuItem menuItem = new MenuItem();
        Logic.addMenuItem(menu, menuItem);
        return Optional.of(menuItem);
    }

    /**
     *
     *  Initiates a ticket + statement duo
     *
     * @param ticket ticket to use in duo
     * @param statement statement to use in duo
     * @return true if the initialization was successful
     */
    public boolean initTicketStatement(Ticket ticket, Statement statement) {
        Logic.addTicket(this, ticket);
        Logic.addStatement(this, statement);
        Logic.bindTicketStatement(ticket, statement);
        statement.setLatePenalty(this.latePenaltyPolicy);
        ticket.setDate(new Date());
        statement.setDue(ticket.getDate());
        Optional<Client> client = createClient();
        if (client.isPresent()) { statement.setClient(client.get()); }
        else { return false; }
        return true;
    }

    public void fillWithDummyValues() {
        Logic.addProduct(this, new Product(
                this, true, "AAA", 10, Tax.ALCOHOL, true, ProductType.ALCOHOL, ""
        ));
        Logic.addProduct(this, new Product(
                this, true, "BBB", 20, Tax.ALCOHOL, true, ProductType.ALCOHOL, ""
        ));
        Logic.addClient(this, new Client(this, "Alice", "ALICE TAX ID", "ALICE CONTACT"));
        Logic.addClient(this, new Client(this, "First", "FIRST TAX ID", "FIRST CONTACT"));
        this.address = "221B Baker Street";

        for (ProductType productType : ProductType.values()) {
            for (int n = 0; n < 6; n++) {
                Logic.addProduct(this, new Product(
                        this, true, productType.toString() + " " + Generation.generateRandomString(6),
                        Generation.generateRandomInt(1, 60), Tax.INSTANT, true, productType,
                        Generation.generateRandomWords(4)
                ));
            }
        }

        Menu menu = this.createMenu().get();
        menu.setCost(50);
        menu.setName("Menu 1");
        this.createMenuItem(menu).get().withName("Entree").withAllowedTags("petit");
        this.createMenuItem(menu).get().withName("Plat").withAllowedTags("plat");
        this.createMenuItem(menu).get().withName("Dessert").withAllowedTags("glace");
        menu = this.createMenu().get();
        menu.setCost(60);
        menu.setName("Menu 2");
        this.createMenuItem(menu).get().withName("Entree").withAllowedTags("sale");
        this.createMenuItem(menu).get().withName("Plat").withAllowedTags("moyen");
        this.createMenuItem(menu).get().withName("Dessert").withAllowedTags("sucre");
    }
}
