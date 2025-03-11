package vn.ptit.project.epl_web.dto.response.clubseasontable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClubSeasonTablesDTO {
    private Long id;
    private int points,ranked,numWins,numLosses,numDraws,goalScores,goalConceded,diff;
    private Long season;
    private Long club;
}
