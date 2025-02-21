package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name="leagues")
@Data
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "league",cascade = CascadeType.ALL)
    private Set<LeagueSeason> leageSeasons;

}
