package vn.ptit.project.epl_web.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.dto.request.league.RequestCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.request.league.RequestUpdateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseUpdateLeagueDTO;
import vn.ptit.project.epl_web.service.LeagueService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

@RestController
@RequestMapping("api/v1/leagues")
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @PostMapping("")
    @ApiMessage("Create new league")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateLeagueDTO> createNewLeague(@Valid @RequestBody RequestCreateLeagueDTO leagueDTO) {
        League newLeague= leagueService.handleCreateLeague(leagueService.requestLeagueDTOtoLeague(leagueDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(leagueService.leagueToResponseCreateLeagueDTO(newLeague));
    }
    @PutMapping("")
    @ApiMessage("Update a league")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdateLeagueDTO> updateLeague(@Valid @RequestBody RequestUpdateLeagueDTO leagueDTO) throws InvalidRequestException {
        League league=leagueService.findByLeagueId(leagueDTO.getId());
        if(league==null) {
            throw new InvalidRequestException("League with id = " + leagueDTO.getId() + " not found.");
        }
        League updatedLeague=this.leagueService.handleUpdateLeague(league, leagueDTO);
        return ResponseEntity.ok().body(this.leagueService.leagueToResponseUpdateLeagueDTO(updatedLeague));
    }
    @GetMapping("/{id}")
    @ApiMessage("Fetch a league")
    public ResponseEntity<ResponseUpdateLeagueDTO> findLeagueById(@PathVariable("id") Long id) throws InvalidRequestException {
        League league=this.leagueService.findByLeagueId(id);
        if (league==null) {
            throw new InvalidRequestException("League with id = " + id + " not found");
        }
        return ResponseEntity.ok().body(leagueService.leagueToResponseUpdateLeagueDTO(league));
    }
    @GetMapping("")
    @ApiMessage("fetch all leagues")
    public ResponseEntity<ResultPaginationDTO> fetchAllLeagues(@Filter Specification<League> spec, Pageable pageable) {
        return ResponseEntity.ok(this.leagueService.fetchAllLeagues(spec,pageable));
    }
    @DeleteMapping("{id}")
    @ApiMessage("Delete a league")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteLeague(@PathVariable("id") Long leagueId) throws InvalidRequestException {
            if(leagueService.findByLeagueId(leagueId)==null) {
                throw new InvalidRequestException("League with id = " + leagueId + " not found.");
            }
            this.leagueService.deleteLeague(leagueId);
            return ResponseEntity.ok(null);
    }
}
