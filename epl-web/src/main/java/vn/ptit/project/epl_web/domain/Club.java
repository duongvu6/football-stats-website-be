package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity

@Table(name = "clubs")
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String stadiumName;
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<CoachClub> coachClubs;
    @OneToMany(mappedBy = "club")
    private List<ClubSeasonTable> clubSeasonTables;
    @OneToMany(mappedBy = "club")
    private List<TransferHistory> transferHistories;
    private String imageUrl;
}
