package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "subcategories")

public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcategory_seq")
    @SequenceGenerator(name = "subcategory_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty(message = "Name must not be empty!")
    private String name;

    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
    private Set<MenuItem> menuItems = new LinkedHashSet<>();

    @ManyToOne(cascade = {REFRESH, PERSIST, MERGE, DETACH})
    private Category category;


}