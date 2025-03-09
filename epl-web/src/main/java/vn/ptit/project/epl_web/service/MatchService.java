package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.HeadCoach;
import vn.ptit.project.epl_web.domain.Match;
import vn.ptit.project.epl_web.domain.MatchAction;
import vn.ptit.project.epl_web.dto.request.match.RequestCreateMatchDTO;
import vn.ptit.project.epl_web.dto.request.match.RequestUpdateMatchDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.match.MatchActionDTO;
import vn.ptit.project.epl_web.dto.response.match.ResponseCreateMatchDTO;
import vn.ptit.project.epl_web.dto.response.match.ResponseUpdateMatchDTO;
import vn.ptit.project.epl_web.repository.MatchRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final ModelMapper modelMapper;
    private final LeagueSeasonService leagueSeasonService;
    private final ClubService clubService;
    private final MatchActionService matchActionService;
    public MatchService(MatchRepository matchRepository, ModelMapper modelMapper, LeagueSeasonService leagueSeasonService, ClubService clubService, MatchActionService matchActionService) {
        this.matchRepository = matchRepository;
        this.modelMapper = modelMapper;
        this.leagueSeasonService = leagueSeasonService;
        this.clubService = clubService;
        this.matchActionService = matchActionService;
    }

    public Match requestCreateMatchDTOtoMatch(RequestCreateMatchDTO requestCreateMatchDTO) {
        Match match = modelMapper.map(requestCreateMatchDTO, Match.class);
        match.setHost(clubService.getClubById(requestCreateMatchDTO.getHostId()).get());
        match.setAway(clubService.getClubById(requestCreateMatchDTO.getAwayId()).get());
        match.setSeason(leagueSeasonService.findByLeagueSeasonId(requestCreateMatchDTO.getSeasonId()));
        return match;
    }
    public Match handleCreateMatch(Match match) {
        return matchRepository.save(match);
    }
    public ResponseCreateMatchDTO matchToResponseCreateMatchDTO(Match match) {
        ResponseCreateMatchDTO responseCreateMatchDTO = modelMapper.map(match, ResponseCreateMatchDTO.class);
        responseCreateMatchDTO.setHostId(match.getHost().getId());
        responseCreateMatchDTO.setAwayId(match.getAway().getId());
        responseCreateMatchDTO.setSeasonId(match.getSeason().getId());
        return responseCreateMatchDTO;
    }
    public Match handleUpdateMatch(RequestUpdateMatchDTO updateMatchDTO) {
        Match match = modelMapper.map(updateMatchDTO, Match.class);
        return matchRepository.save(match);
    }
    public ResponseUpdateMatchDTO matchToResponseUpdateMatchDTO(Match match) {
        ResponseUpdateMatchDTO responseUpdateMatchDTO = modelMapper.map(match, ResponseUpdateMatchDTO.class);
        List<MatchActionDTO> matchActionDTOList = new ArrayList<>();
        for(MatchAction x: match.getMatchActions()) {
            matchActionDTOList.add(matchActionService.MatchActionToMatchActionDTO(x));
        }
        responseUpdateMatchDTO.setMatchActionDTOS(matchActionDTOList);
        return responseUpdateMatchDTO;
    }
    public Match findMatchById(Long id) {
        return matchRepository.findById(id).orElse(null);
    }
    public ResultPaginationDTO fetchAllMatches(Specification<Match> spe, Pageable pageable) {
        Page<Match> matchPage = this.matchRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(matchPage.getTotalPages());
        meta.setTotal(matchPage.getTotalElements());
        result.setMeta(meta);
        List<ResponseUpdateMatchDTO> matchDTOList = matchPage.getContent().stream().map(this::matchToResponseUpdateMatchDTO).collect(Collectors.toList());
        result.setResult(matchDTOList);
        return result;
    }
    public void deleteMatchById(Long id) {
        matchRepository.deleteById(id);
    }

}
