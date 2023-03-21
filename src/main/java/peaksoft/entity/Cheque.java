package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cheques")
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private int priceAverage;
    private LocalDate createAt;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},fetch = FetchType.EAGER)
    @JoinTable(name = "cheques_menu_items",
            joinColumns = @JoinColumn(name = "cheque_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_items_id"))
    private Set<MenuItem> menuItems = new LinkedHashSet<>();
}