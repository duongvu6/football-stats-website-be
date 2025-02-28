package vn.ptit.project.epl_web.dto.request.league;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.domain.LeagueSeason;

import java.util.List;

@Setter
@Getter
public class RequestUpdateLeagueDTO {
    private Long id;
    private String name;
}
