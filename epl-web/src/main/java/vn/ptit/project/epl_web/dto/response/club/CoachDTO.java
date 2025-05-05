package vn.ptit.project.epl_web.dto.response.club;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Setter
@Getter
public class CoachDTO {
    private Long id;
    private String name;
    private LocalDate dob;
    private List<String> citizenships;
}
