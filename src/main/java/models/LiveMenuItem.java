package models;

import jakarta.persistence.*;

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
    }

    public boolean allowed(Product product) {
        Set<String> productTags = new HashSet<>(List.of(product.getTags().split(" ")));
        productTags.addAll(product.getProductType().toAllStrings());
        Set<String> allowedTags = new HashSet<>(List.of(this.allowedTags.split(" ")));
        return productTags.stream().anyMatch(allowedTags::contains);
    }
}
