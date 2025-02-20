package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Match {
    @ManyToOne
    @JoinColumn(name="host_id")
    @Id
    private Club host;
    @ManyToOne
    @JoinColumn(name="away_id")
    @Id
    private Club away;
    @ManyToOne
    @JoinColumn(name="season_id")
    @Id
    private LeagueSeason season;
    private int round,awayScore,hostScore;
    private LocalDateTime date;


}
