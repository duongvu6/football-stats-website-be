package vn.ptit.project.epl_web.dto.response.matchaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseCreateMatchActionDTO {
    private Long id;
    private String action;
    private int minute;
    private Long matchId;
    private Long playerId;
}
