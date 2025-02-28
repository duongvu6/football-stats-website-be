package vn.ptit.project.epl_web.dto.response.leagueseason;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ResponseCreateLeagueSeasonDTO {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private List<String> clubSeasonTables;
}
