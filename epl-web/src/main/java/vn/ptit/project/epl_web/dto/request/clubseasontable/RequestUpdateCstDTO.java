package vn.ptit.project.epl_web.dto.request.clubseasontable;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestUpdateCstDTO {
    @NotNull(message = "ID must not be null")
    private Long id;
    private int points,ranked,numWins,numLosses,numDraws,goalScores,goalConceded,diff;
    private Long season;
    private Long club;

}
