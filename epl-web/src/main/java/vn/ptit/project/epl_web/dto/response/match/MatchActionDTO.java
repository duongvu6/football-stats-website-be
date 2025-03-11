package vn.ptit.project.epl_web.dto.response.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchActionDTO {
    private Long id;
    private String action;
    private int minute;
    private long player;
}
