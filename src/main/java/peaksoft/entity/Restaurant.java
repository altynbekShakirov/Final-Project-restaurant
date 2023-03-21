package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String location;
    private String restType;

    private int numberOfEmployees;
    private int service;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuItem> menuItems = new LinkedHashSet<>();

}