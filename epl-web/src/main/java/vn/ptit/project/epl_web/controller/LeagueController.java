package vn.ptit.project.epl_web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.dto.request.league.RequestCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.request.league.RequestUpdateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseUpdateLeagueDTO;
import vn.ptit.project.epl_web.service.LeagueService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;

@RestController
@RequestMapping("api/v1/leagues")
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @PostMapping("")
    @ApiMessage("Create new league")
    @PreAuthorize("ADMIN")
    public ResponseEntity<ResponseCreateLeagueDTO> createNewLeague(@Valid @RequestBody RequestCreateLeagueDTO leagueDTO) {
        League newLeague= leagueService.handleCreateLeague(leagueService.requestLeagueDTOtoLeague(leagueDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(leagueService.leagueToResponseCreateLeagueDTO(newLeague));
    }
    @PutMapping("")
    @ApiMessage("Update a league")
    @PreAuthorize("ADMIN")
    public ResponseEntity<ResponseUpdateLeagueDTO> updateLeague(@Valid @RequestBody RequestUpdateLeagueDTO leagueDTO) {
        League league=leagueService.findByLeagueId(leagueDTO.getId());
        League updatedLeague=this.leagueService.handleUpdateLeague(league, leagueDTO);
        return ResponseEntity.ok().body(this.leagueService.leagueToResponseUpdateLeagueDTO(updatedLeague));
    }
}
