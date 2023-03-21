package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private boolean isVegetarian;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;



    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private StopList stopList;

    @ManyToOne( cascade = CascadeType.ALL )
    private Subcategory subcategory ;

    @ManyToMany(mappedBy = "menuItems", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Cheque> cheques = new LinkedHashSet<>();

}