package vn.ptit.project.epl_web.dto.request.transferhistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class RequestCreateTransferHistoryDTO {
    private LocalDate date;
    private String type;
    private double playerValue,fee;
    private Long player;
    private Long club;
}
