package vn.ptit.project.epl_web.dto.request.transferhistory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RequestCreateTransferHistoryDTO {
    private LocalDateTime date;
    private String type;
    private double playerValue,fee;
    private int player;
    private int club;
}
