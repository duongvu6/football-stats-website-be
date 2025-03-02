package vn.ptit.project.epl_web.dto.response.club;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.domain.ClubSeasonTable;
import vn.ptit.project.epl_web.domain.CoachClub;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;

import java.util.List;

@Getter
@Setter
public class ResponseClubDTO {
    private Long id;
    private String name;
    private String country;
    private String stadiumName;

//    private List<CoachClub> coachClubs;
//    private List<ClubSeasonTable> clubSeasonTables;
    private List<ResponseCreateTransferHistoryDTO> transferHistories;
}
