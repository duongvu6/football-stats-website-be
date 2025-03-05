package vn.ptit.project.epl_web.dto.response.coachclub;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCoachClubDTO {
    private Long id;
    private String headCoach;
    private String club;
    private LocalDate startDate;
    private LocalDate endDate;
}
