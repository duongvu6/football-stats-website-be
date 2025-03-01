package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class LeagueSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable=true)
    private LocalDateTime startDate;
    @Column(nullable=true)
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;
    @OneToMany(mappedBy = "season")
    private List<ClubSeasonTable> clubSeasonTables;

}
