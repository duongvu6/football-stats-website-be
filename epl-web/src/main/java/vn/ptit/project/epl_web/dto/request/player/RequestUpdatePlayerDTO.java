package vn.ptit.project.epl_web.dto.request.player;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RequestUpdatePlayerDTO {
    private Long id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotNull(message = "Date of birth must not be blank")
    private LocalDate dob;
    @NotNull(message = "Shirt number must not be blank")
    @Positive(message = "Shirt number must be positive number")
    private int shirtNumber;
    @Positive(message = "Market value must be positive number")
    private double marketValue;
    @NotNull(message = "Citizenship must not be null")
    private List<String> citizenships;
    @NotNull(message = "Positions must not be null")
    private List<String> positions;
    private String imageUrl;
}
