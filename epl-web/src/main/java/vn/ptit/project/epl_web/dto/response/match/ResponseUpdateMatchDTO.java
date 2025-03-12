package vn.ptit.project.epl_web.dto.response.match;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.club.ResponseClubDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.LeagueSeasonDTO;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ResponseUpdateMatchDTO {
    private Long id;
    private ResponseClubDTO host;
    private ResponseClubDTO away;
    private LeagueSeasonDTO season;
    private int round,awayScore,hostScore;
    private LocalDateTime date;
    private List<MatchActionDTO> matchActions;
}
