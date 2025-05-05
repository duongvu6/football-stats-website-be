package vn.ptit.project.epl_web.dto.request.club;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUpdateClubDTO {
    private Long id;
    @NotBlank(message = "Name must not be null")
    private String name;
    @NotBlank(message = "Country must not be null")
    private String country;
    private String stadiumName;
    private String imageUrl;
}
