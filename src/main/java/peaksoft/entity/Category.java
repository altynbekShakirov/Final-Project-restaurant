package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;


    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    private Set<Subcategory> subcategories = new LinkedHashSet<>();

}