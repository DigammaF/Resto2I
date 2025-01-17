package models;

import jakarta.persistence.*;
import logic.Logic;

/**
 *
 *  A product slot in a LiveMenu
 *
 */
@Entity
public class MenuItem {
    public static final String DEFAULT_NAME = "unnamed";
    public static final String DEFAULT_TAGS = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Menu menu;

    @Column(name = "allowedTags", nullable = false)
    private String allowedTags;

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public MenuItem() {
        this.name = DEFAULT_NAME;
        this.allowedTags = DEFAULT_TAGS;
    }

    public boolean allowed(Product product) {
        return Logic.tagsAllowProduct(product, allowedTags);
    }

    public MenuItem withName(String name) {
        this.name = name;
        return this;
    }

    public MenuItem withAllowedTags(String allowedTags) {
        this.allowedTags = allowedTags;
        return this;
    }
}
