package vn.ptit.project.epl_web.dto.response.match;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ResponseCreateMatchDTO {
    private Long id;
    private Long hostId;
    private Long awayId;
    private Long seasonId;
    private int round,awayScore,hostScore;
    private LocalDateTime date;
}
