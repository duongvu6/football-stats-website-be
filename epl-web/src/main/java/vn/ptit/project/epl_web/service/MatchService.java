package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.*;
import vn.ptit.project.epl_web.dto.request.match.RequestCreateMatchDTO;
import vn.ptit.project.epl_web.dto.request.match.RequestUpdateMatchDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.match.MatchActionDTO;
import vn.ptit.project.epl_web.dto.response.match.ResponseCreateMatchDTO;
import vn.ptit.project.epl_web.dto.response.match.ResponseUpdateMatchDTO;
import vn.ptit.project.epl_web.repository.MatchRepository;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final ModelMapper modelMapper;
    private final LeagueSeasonService leagueSeasonService;
    private final ClubService clubService;
    private final MatchActionService matchActionService;
    private final ClubSeasonTableService clubSeasonTableService;
    public MatchService(MatchRepository matchRepository, ModelMapper modelMapper, LeagueSeasonService leagueSeasonService, ClubService clubService, MatchActionService matchActionService, ClubSeasonTableService clubSeasonTableService) {
        this.matchRepository = matchRepository;
        this.modelMapper = modelMapper;
        this.leagueSeasonService = leagueSeasonService;
        this.clubService = clubService;
        this.matchActionService = matchActionService;
        this.clubSeasonTableService = clubSeasonTableService;
    }

    public Match requestCreateMatchDTOtoMatch(RequestCreateMatchDTO requestCreateMatchDTO) {
        Match match = modelMapper.map(requestCreateMatchDTO, Match.class);
        match.setHost(clubService.getClubById(requestCreateMatchDTO.getHost()).get());
        match.setAway(clubService.getClubById(requestCreateMatchDTO.getAway()).get());
        match.setSeason(leagueSeasonService.findByLeagueSeasonId(requestCreateMatchDTO.getSeason()));
        return match;
    }
    public void handleCreateMatch(Match match) {
        matchRepository.save(match);
        this.updateLeagueTableOnMatch(match);
    }
    public ResponseCreateMatchDTO matchToResponseCreateMatchDTO(Match match) {
        ResponseCreateMatchDTO responseCreateMatchDTO = modelMapper.map(match, ResponseCreateMatchDTO.class);
        responseCreateMatchDTO.setHost(this.clubService.clubToResponseClubDTO(match.getHost()));
        responseCreateMatchDTO.setAway(this.clubService.clubToResponseClubDTO(match.getAway()));
        responseCreateMatchDTO.setSeason(this.leagueSeasonService.leagueSeasonToLeagueSeasonDTO(match.getSeason()));
        return responseCreateMatchDTO;
    }
    public Match handleUpdateMatch(RequestUpdateMatchDTO updateMatchDTO) throws InvalidRequestException {
        Match match = modelMapper.map(updateMatchDTO, Match.class);
        Optional<Club> host = this.clubService.getClubById(updateMatchDTO.getHost());
        Optional<Club> away = this.clubService.getClubById(updateMatchDTO.getAway());
        LeagueSeason leagueSeason = this.leagueSeasonService.findByLeagueSeasonId(updateMatchDTO.getSeason());
        if (host.isEmpty() || away.isEmpty()) {
            throw new InvalidRequestException("Host or away club not found");
        }
        match.setAway(away.get());
        match.setHost(host.get());
        match.setSeason(leagueSeason);
        this.updateLeagueTableOnMatch(match);
        return matchRepository.save(match);
    }
    public ResponseUpdateMatchDTO matchToResponseUpdateMatchDTO(Match match) {
        ResponseUpdateMatchDTO responseUpdateMatchDTO = modelMapper.map(match, ResponseUpdateMatchDTO.class);
        responseUpdateMatchDTO.setHost(this.clubService.clubToResponseClubDTO(match.getHost()));
        responseUpdateMatchDTO.setAway(this.clubService.clubToResponseClubDTO(match.getAway()));
        responseUpdateMatchDTO.setSeason(this.leagueSeasonService.leagueSeasonToLeagueSeasonDTO(match.getSeason()));
        List<MatchActionDTO> matchActionDTOList = new ArrayList<>();
        for(MatchAction x: match.getMatchActions()) {
            matchActionDTOList.add(matchActionService.MatchActionToMatchActionDTO(x));
        }
        responseUpdateMatchDTO.setMatchActions(matchActionDTOList);
        return responseUpdateMatchDTO;
    }
    public Match findMatchById(Long id) {
        return matchRepository.findById(id).orElse(null);
    }
    public ResultPaginationDTO fetchAllMatches(Specification<Match> spe, Pageable pageable) {
        Page<Match> matchPage = this.matchRepository.findAll(spe, pageable);
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
        Match match = matchRepository.findById(id).orElse(null);
        if (match != null) {
            // Delete all associated match actions first
            if (match.getMatchActions() != null && !match.getMatchActions().isEmpty()) {
                for (MatchAction action : match.getMatchActions()) {
                    matchActionService.deleteMatchActionById(action.getId());
                }
            }
            // Now it's safe to delete the match
            matchRepository.deleteById(id);
        }
    }

    private void updateLeagueTableOnMatch(Match match) {
        LeagueSeason leagueSeason = this.leagueSeasonService.findByLeagueSeasonId(match.getSeason().getId());
        if (leagueSeason != null) {
            Club host = match.getHost();
            Club away = match.getAway();
            ArrayList<ClubSeasonTable> clubSeasonTables = new ArrayList<>(leagueSeason.getClubSeasonTables());
            for (ClubSeasonTable cst : clubSeasonTables) {
                if (cst.getClub().getName().equals(host.getName())) {
                    if (match.getHostScore() > match.getAwayScore()) {
                        cst.setNumWins(cst.getNumWins() + 1);
                        cst.setPoints(cst.getPoints() + 3);
                    } else if (match.getHostScore() == match.getAwayScore()) {
                        cst.setNumDraws(cst.getNumDraws() + 1);
                        cst.setPoints(cst.getPoints() + 1);
                    } else {
                        cst.setNumLosses(cst.getNumLosses() + 1);
                    }
                    cst.setGoalScores(cst.getGoalScores() + match.getHostScore());
                    cst.setGoalConceded(cst.getGoalConceded() + match.getAwayScore());
                    cst.setDiff(cst.getGoalScores() - cst.getGoalConceded());
                    this.clubSeasonTableService.handleUpdateClubSeasonTable(cst);
                } else if (cst.getClub().getName().equals(away.getName())) {
                    if (match.getAwayScore()> match.getHostScore()) {
                        cst.setNumWins(cst.getNumWins() + 1);
                        cst.setPoints(cst.getPoints() + 3);
                    } else if (match.getHostScore() == match.getAwayScore()) {
                        cst.setNumDraws(cst.getNumDraws() + 1);
                        cst.setPoints(cst.getPoints() + 1);
                    } else {
                        cst.setNumLosses(cst.getNumLosses() + 1);
                    }
                    cst.setGoalScores(cst.getGoalScores() + match.getAwayScore());
                    cst.setGoalConceded(cst.getGoalConceded() + match.getHostScore());
                    cst.setDiff(cst.getGoalScores() - cst.getGoalConceded());
                    this.clubSeasonTableService.handleUpdateClubSeasonTable(cst);
                }
            }

        }

    }

}