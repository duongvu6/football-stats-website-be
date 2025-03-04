package vn.ptit.project.epl_web.dto.response.league;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseCreateLeagueSeasonDTO;

import java.util.List;

@Getter
@Setter
public class ResponseUpdateLeagueDTO {
    private Long id;
    private String name;
    private List<LeagueSeasonDTO> leagueSeasons;
}
