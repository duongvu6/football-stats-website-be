package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"season_id", "club_id"})
})
public class ClubSeasonTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=true)
    private int points,ranked,numWins,numLosses,numDraws,goalScores,goalConceded,diff;
    @ManyToOne
    @JoinColumn(name = "season_id")
    private LeagueSeason season;
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
