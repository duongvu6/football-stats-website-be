package vn.ptit.project.epl_web.dto.response.club;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUpdateClubDTO {
    private Long id;
    private String name;
    private String country;
    private String stadiumName;
    //TODO add detail
}
