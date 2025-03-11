package vn.ptit.project.epl_web.dto.response.leagueseason;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.clubseasontable.ClubSeasonTablesDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ResponseUpdateLeaguesSeasonDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long league;
    private List<ClubSeasonTablesDTO> clubSeasonTables;
}
