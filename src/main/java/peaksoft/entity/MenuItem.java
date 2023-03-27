package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.REFRESH;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank(message = "Name must not be empty!")

    private String name;
    @NotBlank(message = "Image must not be empty!")
    private String image;
    private BigDecimal price;
    @NotBlank(message = "Description must not be empty!")
    private String description;
    private boolean isVegetarian;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @OneToOne(mappedBy = "menuItem", cascade = ALL)
    private StopList stopList;
    @ManyToOne(cascade = CascadeType.ALL)
    private Subcategory subcategory;
    @ManyToMany(mappedBy = "menuItems", cascade = {CascadeType.PERSIST, CascadeType.MERGE, REFRESH, CascadeType.DETACH})
    private Set<Cheque> cheques = new LinkedHashSet<>();

}