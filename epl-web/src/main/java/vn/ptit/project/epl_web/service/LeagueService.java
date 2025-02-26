package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.dto.request.league.RequestCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseCreateLeagueDTO;
import vn.ptit.project.epl_web.repository.LeagueRepository;
@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final ModelMapper mapper;

    public LeagueService(LeagueRepository leagueRepository, ModelMapper mapper) {
        this.leagueRepository = leagueRepository;
        this.mapper = mapper;
    }
    public League requestLeagueDTOtoLeague(RequestCreateLeagueDTO dto)
    {
        return this.mapper.map(dto, League.class);
    }
    public ResponseCreateLeagueDTO leagueToResponseCreateLeagueDTO(League league)
    {
        return this.mapper.map(league, ResponseCreateLeagueDTO.class);
    }
    public League handleCreateLeague(League league) {
         return this.leagueRepository.save(league);
    }

}
