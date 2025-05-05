package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dob;
    private int shirtNumber;
    private double marketValue;
    @ElementCollection
    @CollectionTable(name = "player_citizenship", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "citizenship")
    private List<String> citizenships;
    @ElementCollection
    @CollectionTable(name = "player_position", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "position")
    private List<String> positions;
    @OneToMany(mappedBy = "player")
    private List<TransferHistory> transferHistories;
    @Transactional
    public List<TransferHistory> getTransferHistories() {
        return transferHistories;
    }

    private String imageUrl;
}
