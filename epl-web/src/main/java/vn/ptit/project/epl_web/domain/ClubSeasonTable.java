package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ClubSeasonTable {
    @Column(nullable=true)
    private int points,ranked,numWins,numLosses,numDraws,goalScores,goalConceded,diff;
    @ManyToOne
    @JoinColumn(name = "season_id")
    @Id
    private LeagueSeason season;
    @ManyToOne
    @JoinColumn(name = "club_id")
    @Id
    private Club club;
}
