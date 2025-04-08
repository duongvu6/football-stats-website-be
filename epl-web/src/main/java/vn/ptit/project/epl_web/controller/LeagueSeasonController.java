package vn.ptit.project.epl_web.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.domain.LeagueSeason;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestUpdateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseUpdateLeaguesSeasonDTO;
import vn.ptit.project.epl_web.dto.response.topscorer.ResponseTopGoalScorerDTO;
import vn.ptit.project.epl_web.service.LeagueSeasonService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/league-seasons")
public class LeagueSeasonController {
    private final LeagueSeasonService leagueSeasonService;

    public LeagueSeasonController(LeagueSeasonService leagueSeasonService) {
        this.leagueSeasonService = leagueSeasonService;
    }
    @PostMapping("")
    @ApiMessage("Create new season")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateLeagueSeasonDTO> createLeagueSeason(@Valid @RequestBody RequestCreateLeagueSeasonDTO dto){
            LeagueSeason leagueSeason=this.leagueSeasonService.handleCreatLeagueSeason(leagueSeasonService.requestDTOtoLeagueSeason(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(this.leagueSeasonService.leagueSeasontoDTO(leagueSeason));
    }
    @PutMapping("")
    @ApiMessage("Update a season")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdateLeaguesSeasonDTO> updateLeagueSeason(@RequestBody RequestUpdateLeagueSeasonDTO dto) throws InvalidRequestException {
            LeagueSeason existLeagueSeason=leagueSeasonService.findByLeagueSeasonId(dto.getId());
            if(existLeagueSeason==null){
                throw new InvalidRequestException("Season with id = " + dto.getId() + " not found.");
            }
            LeagueSeason leagueSeason=this.leagueSeasonService.handleUpdateLeagueSeason(existLeagueSeason,dto);
            return ResponseEntity.ok().body(this.leagueSeasonService.seasontoDTO(leagueSeason));
    }
    @GetMapping("{id}")
    @ApiMessage("Fetch a season")
    public ResponseEntity<ResponseUpdateLeaguesSeasonDTO> findLeagueSeasonById(@PathVariable("id") Long id) throws InvalidRequestException {
            LeagueSeason leagueSeason=leagueSeasonService.findByLeagueSeasonId(id);
            if(leagueSeason==null){
                throw new InvalidRequestException("Season with id = " + id + " not found.");
            }
            return ResponseEntity.ok(this.leagueSeasonService.seasontoDTO(leagueSeason));
    }
    @GetMapping("")
    @ApiMessage("Fetch all seasons")
    public ResponseEntity<ResultPaginationDTO> fetchAllSeasons(@Filter Specification<LeagueSeason> spec, Pageable pageable) {
            return ResponseEntity.ok(this.leagueSeasonService.fetchAllLeagueSeasons(spec,pageable));
    }

    @DeleteMapping("{id}")
    @ApiMessage("Delete a season")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteLeagueSeason(@PathVariable("id") Long leagueSeasonId) throws InvalidRequestException {
        if(leagueSeasonService.findByLeagueSeasonId(leagueSeasonId)==null) {
            throw new InvalidRequestException("League with id = " + leagueSeasonId + " not found.");
        }
        this.leagueSeasonService.deleteLeagueSeason(leagueSeasonId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}/top-goal-scorers")
    @ApiMessage("Fetch top goal scorers for a league season")
    public ResponseEntity<ArrayList<ResponseTopGoalScorerDTO>> getTopGoalScorers(@PathVariable Long id) {
        ArrayList<ResponseTopGoalScorerDTO> topGoalScorers = leagueSeasonService.getTopGoalScorers(id);
        return ResponseEntity.ok(topGoalScorers);
    }

    @GetMapping("/{id}/top-assists")
    @ApiMessage("Fetch top assists for a league season")
    public ResponseEntity<ArrayList<ResponseTopGoalScorerDTO>> getTopAssists(@PathVariable Long id) {
        ArrayList<ResponseTopGoalScorerDTO> topGoalScorers = leagueSeasonService.getTopAssists(id);
        return ResponseEntity.ok(topGoalScorers);
    }
    @GetMapping("/{id}/top-yellow-cards")
    @ApiMessage("Fetch top yellow cards for a league season")
    public ResponseEntity<ArrayList<ResponseTopGoalScorerDTO>> getTopYellowCards(@PathVariable Long id) {
        ArrayList<ResponseTopGoalScorerDTO> topGoalScorers = leagueSeasonService.getTopYellowCards(id);
        return ResponseEntity.ok(topGoalScorers);
    }
    @GetMapping("/{id}/top-red-cards")
    @ApiMessage("Fetch top red cards for a league season")
    public ResponseEntity<ArrayList<ResponseTopGoalScorerDTO>> getTopRedCards(@PathVariable Long id) {
        ArrayList<ResponseTopGoalScorerDTO> topGoalScorers = leagueSeasonService.getTopRedCards(id);
        return ResponseEntity.ok(topGoalScorers);
    }
    
}
