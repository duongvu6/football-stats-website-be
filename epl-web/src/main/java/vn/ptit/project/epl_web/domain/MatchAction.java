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
//    private Club hostTeam;
//    @ManyToOne
    @JoinColumn(name="away_id")
//    private Club awayTeam;
//    @ManyToOne
    @JoinColumn(name="season_id")
    private Match match;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

}
