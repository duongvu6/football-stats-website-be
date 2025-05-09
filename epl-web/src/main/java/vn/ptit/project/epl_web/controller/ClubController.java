package vn.ptit.project.epl_web.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.dto.request.club.RequestCreateClubDTO;
import vn.ptit.project.epl_web.dto.request.club.RequestUpdateClubDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.club.ResponseClubDTO;
import vn.ptit.project.epl_web.dto.response.club.ResponseCreateClubDTO;
import vn.ptit.project.epl_web.dto.response.club.ResponseUpdateClubDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.LeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponsePlayerDTO;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.service.ClubService;
import vn.ptit.project.epl_web.service.LeagueSeasonService;
import vn.ptit.project.epl_web.service.PlayerService;
import vn.ptit.project.epl_web.service.TransferHistoryService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubController {
    private final ClubService clubService;
    private final PlayerService playerService;
    private final TransferHistoryService transferHistoryService;
    private final LeagueSeasonService leagueSeasonService;
    public ClubController(ClubService clubService, PlayerService playerService, TransferHistoryService transferHistoryService, LeagueSeasonService leagueSeasonService) {
        this.clubService = clubService;
        this.playerService = playerService;
        this.transferHistoryService = transferHistoryService;
        this.leagueSeasonService = leagueSeasonService;
    }

    @PostMapping("")
    @ApiMessage("Create a club")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateClubDTO> createNewClub(@Valid @RequestBody RequestCreateClubDTO clubDTO) {
        Club newClub = this.clubService.handleCreateClub(this.clubService.requestCreateClubToClub(clubDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clubService.clubToResponseCreateClubDTO(newClub));
    }
    @PutMapping("")
    @ApiMessage("Update a club")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdateClubDTO> updateAClub(@Valid @RequestBody RequestUpdateClubDTO clubDTO) throws InvalidRequestException {
        Optional<Club> club = this.clubService.getClubById(clubDTO.getId());
        if (club.isEmpty()) {
            throw new InvalidRequestException("Club with id = " + clubDTO.getId() + " not found.");
        }
        Club updatedClub = this.clubService.handleUpdateClub(club.get(), clubDTO);
        return ResponseEntity.ok().body(this.clubService.clubToResponseUpdateClub(updatedClub));
    }
    @GetMapping("/{id}")
    @ApiMessage("Fetch a club")
    public ResponseEntity<ResponseClubDTO> getAClub(@PathVariable Long id) throws InvalidRequestException {
        Optional<Club> club = this.clubService.getClubById(id);
        if (club.isEmpty()) {
            throw new InvalidRequestException("Club with id = " + id + " not found.");
        }
        return ResponseEntity.ok(this.clubService.clubToResponseClubDTO(club.get()));
    }

    @GetMapping("")
    @ApiMessage("Fetch all clubs")
    public ResponseEntity<ResultPaginationDTO> fetchAllClubs(
            @Filter Specification<Club> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.clubService.fetchAllClubs(spec, pageable));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a club")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAClub(@PathVariable Long id) throws InvalidRequestException {
        this.clubService.handleDeleteClub(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}/squad")
    @ApiMessage("Fetch squad list for a club in a specific season")
    public ResponseEntity<List<ResponsePlayerDTO>> getSquadByClubAndSeason(
            @PathVariable("id") Long clubId,
            @RequestParam("seasonId") Long seasonId
    ) {
        List<ResponsePlayerDTO> squad = playerService.getSquadByClubAndSeason(clubId, seasonId);
        return ResponseEntity.ok(squad);
    }
    @GetMapping("/{id}/transfers")
    @ApiMessage("Fetch all transfers for a club in a specific season")
    public ResponseEntity<List<ResponseCreateTransferHistoryDTO>> getAllTransfersByClubAndSeason(
            @PathVariable("id") Long clubId,
            @RequestParam("seasonId") Long seasonId
    ) {
        List<ResponseCreateTransferHistoryDTO> transfers = transferHistoryService.getAllTransfersByClubAndSeason(clubId, seasonId);
        return ResponseEntity.ok(transfers);
    }
    @GetMapping("/{id}/seasons")
    @ApiMessage("Fetch all league seasons for a club")
    public ResponseEntity<List<LeagueSeasonDTO>> getLeagueSeasonsByClubId(
            @PathVariable("id") Long clubId) throws InvalidRequestException {

        Optional<Club> club = this.clubService.getClubById(clubId);
        if (club.isEmpty()) {
            throw new InvalidRequestException("Club with id = " + clubId + " not found.");
        }

        List<LeagueSeasonDTO> seasons = this.leagueSeasonService.getLeagueSeasonsByClubId(clubId);
        return ResponseEntity.ok(seasons);
    }
}
