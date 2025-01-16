package models;

import jakarta.persistence.*;

/**
 *
 *  A product slot in a LiveMenu
 *
 */
@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu liveMenu) {
        this.menu = liveMenu;
    }

    public String getAllowedTags() {
        return allowedTags;
    }

    public void setAllowedTags(String allowedTags) {
        this.allowedTags = allowedTags;
    }

    @Column(name = "allowedTags", nullable = false)
    private String allowedTags;

    public MenuItem() {
        this.allowedTags = "";
    }
}
