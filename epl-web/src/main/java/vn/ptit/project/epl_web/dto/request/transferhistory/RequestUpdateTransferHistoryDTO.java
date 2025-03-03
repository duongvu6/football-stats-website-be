package vn.ptit.project.epl_web.dto.request.transferhistory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestUpdateTransferHistoryDTO {
    private Long id;
    private LocalDate date;
    private String type;
    private double playerValue, fee;
    private Long player;
    private Long club;
}
