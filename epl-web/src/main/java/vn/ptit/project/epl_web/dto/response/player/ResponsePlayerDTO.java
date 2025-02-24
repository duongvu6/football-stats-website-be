package vn.ptit.project.epl_web.dto.response.player;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ResponsePlayerDTO {
    private Long id;
    private String name;
    private int age;
    private LocalDateTime dob;
    private int shirtNumber;
    private double marketValue;
    private Set<String> citizenships;
    private Set<String> postions;
    private Set<ResponseCreateTransferHistoryDTO> transferHistories;
}
