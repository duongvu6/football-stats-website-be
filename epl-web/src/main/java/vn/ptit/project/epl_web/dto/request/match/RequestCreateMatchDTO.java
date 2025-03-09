package vn.ptit.project.epl_web.dto.request.match;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RequestCreateMatchDTO {
    @NotNull
    private Long hostId;
    @NotNull
    private Long awayId;
    @NotNull
    private Long seasonId;
    @NotNull
    private int round,awayScore,hostScore;
    @NotNull
    private LocalDateTime date;
}
