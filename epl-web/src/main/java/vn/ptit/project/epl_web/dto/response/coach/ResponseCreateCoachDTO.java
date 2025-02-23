package vn.ptit.project.epl_web.dto.response.coach;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class ResponseCreateCoachDTO {
    private Long id;
    private String name;
    private int age;
    private LocalDateTime dob;
    private Set<String> citizenships;
}
