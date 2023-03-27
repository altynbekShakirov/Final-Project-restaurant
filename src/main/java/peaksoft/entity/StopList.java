package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @SequenceGenerator(name = "stop_list_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank(message = "Reason must not be empty!")
    private String reason;
    @NotNull(message = "Date must not be null!!")
    private LocalDate date;
    @OneToOne(cascade = {DETACH, PERSIST, MERGE, REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

}