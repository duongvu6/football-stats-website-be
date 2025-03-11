package vn.ptit.project.epl_web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.CoachClub;
import vn.ptit.project.epl_web.dto.request.coachclub.RequestCreateCoachClubDTO;
import vn.ptit.project.epl_web.dto.request.coachclub.RequestUpdateCoachClubDTO;
import vn.ptit.project.epl_web.dto.response.coachclub.ResponseCoachClubDTO;
import vn.ptit.project.epl_web.service.CoachClubService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

@RestController
@RequestMapping("/api/v1/coach-clubs")
public class CoachClubController {
    private final CoachClubService coachClubService;

    public CoachClubController(CoachClubService coachClubService) {
        this.coachClubService = coachClubService;
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiMessage("Update Coach Club")
    public ResponseEntity<ResponseCoachClubDTO> updateCoachClub(@Valid @RequestBody RequestUpdateCoachClubDTO coachClubDTO) throws InvalidRequestException {
        CoachClub existingCoachClub = this.coachClubService.findById(coachClubDTO.getId());
        if (existingCoachClub == null) {
            throw new InvalidRequestException("Coach club with id = " + coachClubDTO.getId() + " not found.");
        }
        CoachClub updatedCoachClub = this.coachClubService.handleUpdateCoachClub(this.coachClubService.toCoachClub(coachClubDTO));
        return ResponseEntity.ok(this.coachClubService.coachClubToResponseCoachClubDTO(updatedCoachClub));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiMessage("Create Coach Clubs")
    public ResponseEntity<ResponseCoachClubDTO> createCoachClub(@RequestBody RequestCreateCoachClubDTO coachClubDTO) throws InvalidRequestException {
        CoachClub coachClub = this.coachClubService.handleCreateCoachClub(this.coachClubService.toCoachClub(coachClubDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.coachClubService.coachClubToResponseCoachClubDTO(coachClub));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiMessage("Delete a coach club")
    public ResponseEntity<Void> deleteACoachClub(@PathVariable Long id) throws InvalidRequestException {
        this.coachClubService.handleDeleteCoachClubWithId(id);
        return ResponseEntity.ok(null);
    }
}