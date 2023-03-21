package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "stop_lists")
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stop_list_seq")
    @SequenceGenerator(name = "stop_list_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String reason;
    @Column(unique = true)
    private LocalDate date;


    @OneToOne(cascade = {DETACH,PERSIST,MERGE,REFRESH}, orphanRemoval = true)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

}