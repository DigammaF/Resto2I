package models;

import jakarta.persistence.*;

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
    private List<MenuItem> menuItems;

    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public LiveMenu() {
        this.menuItems = new ArrayList<MenuItem>();
    }
}
