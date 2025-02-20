package vn.ptit.project.epl_web.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCreateUserDTO {
    private Long id;
    private String name;
    private String email;
}
