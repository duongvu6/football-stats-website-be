package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private LocalDateTime dob;
    private int shirtNumber;
    private double marketValue;
    @ElementCollection
    @CollectionTable(name = "player_citizenship", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "citizenship")
    private Set<String> citizenships;
    @ElementCollection
    @CollectionTable(name = "player_position", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "position")
    private Set<String> postions;
    @OneToMany(mappedBy = "player")
    private Set<TransferHistory> transferHistories;

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dob=" + dob +
                ", shirtNumber=" + shirtNumber +
                ", marketValue=" + marketValue +
                ", citizenships=" + citizenships +
                ", postions=" + postions +
                ", transferHistories=" + transferHistories +
                '}';
    }
}
