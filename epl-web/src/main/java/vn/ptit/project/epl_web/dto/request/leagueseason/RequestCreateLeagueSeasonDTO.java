package vn.ptit.project.epl_web.dto.request.leagueseason;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCreateLeagueSeasonDTO {
    @NotBlank(message = "Name must be not blank")
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Long leagueId;

}
