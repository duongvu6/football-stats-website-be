package vn.ptit.project.epl_web.dto.response.match;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.club.ResponseClubDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.LeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseUpdateLeaguesSeasonDTO;

import java.time.LocalDateTime;

@Setter
@Getter
public class ResponseCreateMatchDTO {
    private Long id;
    private ResponseClubDTO host;
    private ResponseClubDTO away;
    private LeagueSeasonDTO season;
    private int round,awayScore,hostScore;
    private LocalDateTime date;
}
