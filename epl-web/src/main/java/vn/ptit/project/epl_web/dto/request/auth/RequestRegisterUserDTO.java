package vn.ptit.project.epl_web.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestRegisterUserDTO {
    @NotBlank(message = "Please enter your email")
    private String email;
    @NotBlank(message = "Please enter your name")
    private String name;
    @NotBlank(message = "Please enter your password")
    private String password;
}