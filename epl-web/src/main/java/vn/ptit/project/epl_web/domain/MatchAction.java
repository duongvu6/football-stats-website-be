package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;

@Entity
public class MatchAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String action;
    private int minute;
    @ManyToOne
    @JoinColumn(name="host_id")
    private Club host;
    @ManyToOne
    @JoinColumn(name="away_id")
    private Club away;
    @ManyToOne
    @JoinColumn(name="season_id")
    private LeagueSeason season;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

}
