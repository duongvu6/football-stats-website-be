package vn.ptit.project.epl_web.dto.response.match;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.player.ResponsePlayerDTO;

@Getter
@Setter
public class MatchActionDTO {
    private Long id;
    private String action;
    private int minute;
    private ResponsePlayerDTO player;
}
