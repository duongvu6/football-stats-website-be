package vn.ptit.project.epl_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ptit.project.epl_web.domain.LeagueSeason;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.service.LeagueSeasonService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;

@RestController
@RequestMapping("api/v1/leagueseasons")
public class LeagueSeasonController {
    private final LeagueSeasonService leagueSeasonService;

    public LeagueSeasonController(LeagueSeasonService leagueSeasonService) {
        this.leagueSeasonService = leagueSeasonService;
    }
    @PostMapping("")
    @ApiMessage("Create new season")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateLeagueSeasonDTO> createLeagueSeason(RequestCreateLeagueSeasonDTO dto){
            LeagueSeason leagueSeason=this.leagueSeasonService.handleCreatLeagueSeason(leagueSeasonService.requestDTOtoLeagueSeason(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(this.leagueSeasonService.leagueSeasontoDTO(leagueSeason));
    }


    
}
