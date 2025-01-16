package models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private LiveMenu liveMenu;

    @Column(name = "allowedTags", nullable = false)
    private String allowedTags;

    public boolean allowed(Product product) {
        Set<String> productTags = new HashSet<>(List.of(product.getTags().split(" ")));
        productTags.addAll(product.getProductType().toAllStrings());
        Set<String> allowedTags = new HashSet<>(List.of(this.allowedTags.split(" ")));
        return productTags.stream().anyMatch(allowedTags::contains);
    }
}
