package vn.ptit.project.epl_web.dto.response.club;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PlayerDTO {
    private Long id;
    private String name;
    private LocalDate dob;
    private int shirtNumber;
    private double marketValue;
    private List<String> citizenships;
    private List<String> positions;
}
