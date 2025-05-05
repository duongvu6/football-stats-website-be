package vn.ptit.project.epl_web.dto.response.player;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ResponseCreatePlayerDTO {
    private Long id;
    private String name;
    private int age;
    private LocalDate dob;
    private int shirtNumber;
    private double marketValue;
    private List<String> citizenships;
    private List<String> positions;
    private String imageUrl;
}
