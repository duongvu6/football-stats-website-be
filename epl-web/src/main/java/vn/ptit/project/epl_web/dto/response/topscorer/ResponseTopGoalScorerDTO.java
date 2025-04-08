package vn.ptit.project.epl_web.dto.response.topscorer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTopGoalScorerDTO {
    private Long playerId;
    private String playerName;
    private Long goals;
    private Long assists;
    private String currentClub;
    private Long yellowCards;
    private Long redCards;
}