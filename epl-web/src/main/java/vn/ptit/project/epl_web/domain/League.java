package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="leagues")
@Data
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "league",cascade = CascadeType.ALL)
    private List<LeagueSeason> leageSeasons;

    public League(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
