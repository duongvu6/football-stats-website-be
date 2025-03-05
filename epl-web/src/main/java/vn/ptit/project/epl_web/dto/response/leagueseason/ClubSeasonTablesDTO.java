package vn.ptit.project.epl_web.dto.response.leagueseason;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.domain.LeagueSeason;

@Setter
@Getter
public class ClubSeasonTablesDTO {
    private Long id;
    private int points,ranked,numWins,numLosses,numDraws,goalScores,goalConceded,diff;
    private Long seasonId;
    private Long clubId;
}
