package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private LocalDateTime dob;
    private int shirt_number;
    private double market_value;
    @ElementCollection
    @CollectionTable(name = "player_citizenship", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "citizenship")
    private Set<String> citizenships;

}
