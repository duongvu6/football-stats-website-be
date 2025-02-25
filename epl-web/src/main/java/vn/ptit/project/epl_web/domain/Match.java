package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="matches")
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
    //MatchAction
//    @OneToMany(mappedBy = "hostTeam")
//    private Set<Club> hostClubs;
//    @OneToMany(mappedBy = "awayTeam")
//    private Set<Club> awayClubs;
    @OneToMany(mappedBy = "match")
    private List<MatchAction> matchActions;


    private int round,awayScore,hostScore;
    private LocalDateTime date;



}
