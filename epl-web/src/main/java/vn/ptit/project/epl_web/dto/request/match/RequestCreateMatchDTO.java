package vn.ptit.project.epl_web.dto.request.match;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RequestCreateMatchDTO {
    @NotNull
    private Long host;
    @NotNull
    private Long away;
    @NotNull
    private Long season;
    @NotNull
    private int round,awayScore,hostScore;
    @NotNull
    private LocalDateTime date;
}
