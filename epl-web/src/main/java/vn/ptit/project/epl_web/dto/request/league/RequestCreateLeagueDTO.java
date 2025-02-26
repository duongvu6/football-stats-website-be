package vn.ptit.project.epl_web.dto.request.league;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCreateLeagueDTO {
    @NotBlank(message = "Name must be not blank")
    private String name;

}
