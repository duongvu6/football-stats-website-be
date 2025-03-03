package vn.ptit.project.epl_web.dto.request.leagueseason;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.league.LeagueSeasonDTO;

import java.time.LocalDate;

@Getter
@Setter
public class RequestUpdateLeagueSeasonDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long leagueId;
}
