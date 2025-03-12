package vn.ptit.project.epl_web.dto.response.matchaction;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.player.ResponsePlayerDTO;

@Setter
@Getter
public class ResponseUpdateMatchActionDTO {
    private Long id;
    private String action;
    private int minute;
    private Long match;
    private ResponsePlayerDTO player;
}
