package models;

import jakarta.persistence.*;

/**
 *
 *  A group of MenuItem s
 *
 */
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
}
