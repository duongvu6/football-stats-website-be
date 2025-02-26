package vn.ptit.project.epl_web.dto.response.league;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ResponseCreateLeagueDTO {
    private Long id;
    private String name;
    private List<String> leagueSeasons;

}
