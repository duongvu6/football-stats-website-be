package vn.ptit.project.epl_web.dto.response.match;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ResponseUpdateMatchDTO {
    private Long id;
    private Long hostId,awayId,seasonId;
    private int round,awayScore,hostScore;
    private LocalDateTime date;
    private List<MatchActionDTO> matchActionDTOS;
}
