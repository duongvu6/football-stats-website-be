package vn.ptit.project.epl_web.dto.request.coach;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class RequestUpdateCoachDTO {
    private Long id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotNull(message = "Date of birth must not be null")
    private LocalDate dob;
    @NotNull(message = "Citizenship must not be null")
    private Set<String> citizenships;
    private String imageUrl;
}
