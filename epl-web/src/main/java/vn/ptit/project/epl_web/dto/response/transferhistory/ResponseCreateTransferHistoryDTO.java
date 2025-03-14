package vn.ptit.project.epl_web.dto.response.transferhistory;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.player.ResponseCreatePlayerDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseCreateTransferHistoryDTO {
    private Long id;
    private LocalDate date;
    private String type;
    private double playerValue,fee;
    private String player;
    private String club;
    private String previousClub;
}
