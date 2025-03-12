package vn.ptit.project.epl_web.dto.response.clubseasontable;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.club.ResponseClubDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.LeagueSeasonDTO;

@Setter
@Getter
public class ClubSeasonTablesDTO {
    private Long id;
    private int points,ranked,numWins,numLosses,numDraws,goalScores,goalConceded,diff;
    private LeagueSeasonDTO season;
    private ResponseClubDTO club;
}
