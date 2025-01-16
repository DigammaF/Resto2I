package models;

import jakarta.persistence.*;

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

    @ManyToOne
    private Menu menu;

    @OneToMany(mappedBy = "liveMenu", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;
}
