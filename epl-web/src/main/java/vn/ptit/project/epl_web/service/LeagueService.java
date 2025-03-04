package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.dto.request.league.RequestCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.request.league.RequestUpdateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseUpdateLeagueDTO;
import vn.ptit.project.epl_web.repository.LeagueRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final ModelMapper mapper;

    public LeagueService(LeagueRepository leagueRepository, ModelMapper mapper) {
        this.leagueRepository = leagueRepository;
        this.mapper = mapper;
    }
    public League findByLeagueId(Long leagueId) {
        return leagueRepository.findById(leagueId).orElse(null);
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
    public League handleUpdateLeague(League league, RequestUpdateLeagueDTO dto) {
        this.mapper.map(dto, league);
        return this.leagueRepository.save(league);

    }
    public ResponseUpdateLeagueDTO leagueToResponseUpdateLeagueDTO(League league) {
        return this.mapper.map(league, ResponseUpdateLeagueDTO.class);
    }
    public ResultPaginationDTO fetchAllLeagues(Specification<League>spe,Pageable pageable) {
        Page<League> leaguePage=this.leagueRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(leaguePage.getTotalPages());
        meta.setTotal(leaguePage.getTotalElements());
        result.setMeta(meta);
        List<ResponseCreateLeagueDTO> list=leaguePage.getContent().stream().map(this::leagueToResponseCreateLeagueDTO).collect(Collectors.toList());
        result.setResult(list);
        return result;
    }


}
