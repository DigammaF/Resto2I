package models;

import jakarta.persistence.*;
import logic.Tax;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  A concrete realisation of a Menu
 *
 */
@Entity
public class LiveMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Menu menu;

    @OneToMany(mappedBy = "liveMenu", cascade = CascadeType.ALL)
    private List<LiveMenuItem> liveMenuItems;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<LiveMenuItem> getLiveMenuItems() {
        return liveMenuItems;
    }

    public LiveMenu() {
        this.liveMenuItems = new ArrayList<LiveMenuItem>();
    }

    public double getCost() {
        return this.menu.getCost();
    }

    public Tax computeTax() {
        return Tax.INSTANT;
    }

    public double getATICost() {
        return this.computeTax().applied(this.menu.getCost());
    }

    public String getName() {
        return this.menu.getName();
    }
}
