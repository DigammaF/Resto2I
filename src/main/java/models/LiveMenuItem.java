package models;

import jakarta.persistence.*;
import logic.Logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class LiveMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private LiveMenu liveMenu;

    @Column(name = "allowedTags", nullable = false)
    private String allowedTags;

    @OneToOne(cascade = CascadeType.ALL)
    private LiveProduct liveProduct;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "claimed", nullable = false)
    private boolean claimed;

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LiveProduct getLiveProduct() {
        return liveProduct;
    }

    public void setLiveProduct(LiveProduct liveProduct) {
        this.liveProduct = liveProduct;
    }

    public LiveMenu getLiveMenu() {
        return liveMenu;
    }

    public void setLiveMenu(LiveMenu liveMenu) {
        this.liveMenu = liveMenu;
    }

    public String getAllowedTags() {
        return allowedTags;
    }

    public void setAllowedTags(String allowedTags) {
        this.allowedTags = allowedTags;
    }

    public LiveMenuItem() {
        this.allowedTags = "";
        this.name = "";
        this.claimed = true;
    }

    public boolean allowed(Product product) {
        return Logic.tagsAllowProduct(product, allowedTags);
    }
}
