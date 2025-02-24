package vn.ptit.project.epl_web.dto.request.player;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class RequestCreatePlayerDTO {
    @NotBlank(message = "Name must not be blank")
    private String name;
    @Positive(message = "Age must be positive number")
    @NotNull(message = "Age must not be blank")
    private int age;
    @NotNull(message = "Date of birth must not be blank")
    private LocalDateTime dob;
    @NotNull(message = "Shirt number must not be blank")
    @Positive(message = "Shirt number must be positive number")
    private int shirtNumber;
    @Positive(message = "Market value must be positive number")
    private double marketValue;
    @NotNull(message = "Citizenship must not be null")
    private Set<String> citizenships;
    @NotNull(message = "Positions must not be null")
    private Set<String> positions;
}
