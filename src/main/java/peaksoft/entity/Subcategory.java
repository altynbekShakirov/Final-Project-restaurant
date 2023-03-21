package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subcategories")

public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcategory_seq")
    @SequenceGenerator(name = "subcategory_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;



}