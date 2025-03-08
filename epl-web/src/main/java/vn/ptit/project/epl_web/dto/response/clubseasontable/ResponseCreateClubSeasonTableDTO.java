package vn.ptit.project.epl_web.dto.response.clubseasontable;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseCreateClubSeasonTableDTO {
    @NotNull
    private Long id;
    @NotNull
    private int points,ranked,numWins,numLosses,numDraws,goalScores,goalConceded,diff;
    @NotNull
    private Long seasonId,clubId;
}
