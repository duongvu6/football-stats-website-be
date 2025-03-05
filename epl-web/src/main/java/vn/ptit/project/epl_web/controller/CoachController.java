package vn.ptit.project.epl_web.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.HeadCoach;
import vn.ptit.project.epl_web.dto.request.coach.RequestCreateCoachDTO;
import vn.ptit.project.epl_web.dto.request.coach.RequestUpdateCoachDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCreateCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseUpdateCoachDTO;
import vn.ptit.project.epl_web.service.CoachService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/coaches")
public class CoachController {
    private final CoachService coachService;
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @PostMapping("")
    @ApiMessage("Create a new head coach")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateCoachDTO> createNewCoach(@Valid @RequestBody RequestCreateCoachDTO coachDTO) {
        HeadCoach newCoach = this.coachService.handleCreateCoach(this.coachService.requestCreateCoachDTOtoCoach(coachDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.coachService.coachToResponseCreateCoachDTO(newCoach));
    }

    @PutMapping("")
    @ApiMessage("Update a coach")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdateCoachDTO> updateACoach(@Valid @RequestBody RequestUpdateCoachDTO coachDTO) throws InvalidRequestException {
        Optional<HeadCoach> coach = this.coachService.getCoachById(coachDTO.getId());
        if (coach.isEmpty()) {
            throw new InvalidRequestException("Coach with id = " + coachDTO.getId() + " not found");
        }
        HeadCoach updatedCoach = this.coachService.handleUpdateCoach(coach.get(), coachDTO);
        return ResponseEntity.ok(this.coachService.coachToResponseUpdateCoachDTO(updatedCoach));
    }

    @GetMapping("/{id}")
    @ApiMessage("Fetch a coach")
    public ResponseEntity<ResponseCoachDTO> fetchACoach(@PathVariable Long id) throws InvalidRequestException {
        Optional<HeadCoach> coach = this.coachService.getCoachById(id);
        if (coach.isEmpty()) {
            throw new InvalidRequestException("Coach with id = " + id + " not found");
        }
        return ResponseEntity.ok(coachService.coachToResponseCoachDTO(coach.get()));
    }
    @GetMapping("")
    @ApiMessage("Fetch all coaches")
    public ResponseEntity<ResultPaginationDTO> fetchAllCoaches(
            @Filter Specification<HeadCoach> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.coachService.fetchAllCoaches(spec, pageable));
    }
    @DeleteMapping("/{id}")
    @ApiMessage("Delete a coach")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteACoach(@PathVariable Long id ) throws InvalidRequestException {
        this.coachService.handleDeleteCoach(id);
        return ResponseEntity.ok(null);
    }
}
