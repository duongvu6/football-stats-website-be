package vn.ptit.project.epl_web.dto.request.coachclub;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class RequestUpdateCoachClubDTO {
    private Long id;
    private Long headCoach;
    private Long club;
    private LocalDate startDate;
    private LocalDate endDate;
}
