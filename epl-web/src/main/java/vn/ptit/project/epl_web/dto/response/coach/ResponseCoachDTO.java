package vn.ptit.project.epl_web.dto.response.coach;

import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.coachclub.ResponseCreateCoachClubDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter
@Setter
public class ResponseCoachDTO {
    private Long id;
    private String name;
    private int age;
    private LocalDate dob;
    private List<String> citizenships;
    //TODO - add List CoachClubDTO
    private List<ResponseCreateCoachClubDTO> coachClubs;
}
