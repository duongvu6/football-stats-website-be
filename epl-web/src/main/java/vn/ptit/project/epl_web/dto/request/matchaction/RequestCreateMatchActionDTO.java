package vn.ptit.project.epl_web.dto.request.matchaction;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestCreateMatchActionDTO {
    @NotNull
    private String action;
    @NotNull
    private int minute;
    @NotNull
    private Long match;
    @NotNull
    private Long player;
}
