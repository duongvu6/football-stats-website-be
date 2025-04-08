package vn.ptit.project.epl_web.dto.response.club;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.domain.Club;

@Getter
@Setter
public class ClubWinDTO {
    private String club;
    private Integer wins;
    public ClubWinDTO(String club, Integer wins) {
        this.club = club;
        this.wins = wins;
    }
}
