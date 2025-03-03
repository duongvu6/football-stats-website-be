package vn.ptit.project.epl_web.dto.response.league;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class LeagueSeasonDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> clubSeasonTables;
}
