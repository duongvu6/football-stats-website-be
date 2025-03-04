package vn.ptit.project.epl_web.dto.response.coach;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.coachclub.ResponseCreateCoachClubDTO;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class ResponseUpdateCoachDTO {
    private Long id;
    private String name;
    private int age;
    private LocalDate dob;
    private List<String> citizenships;
    private List<ResponseCreateCoachClubDTO> coachClubs;
    
}
