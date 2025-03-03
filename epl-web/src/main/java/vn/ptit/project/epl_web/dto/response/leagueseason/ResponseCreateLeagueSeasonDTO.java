package vn.ptit.project.epl_web.dto.response.leagueseason;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ResponseCreateLeagueSeasonDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long leagueId;
    private List<String> clubSeasonTables;
}
