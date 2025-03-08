package vn.ptit.project.epl_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.project.epl_web.domain.ClubSeasonTable;
import vn.ptit.project.epl_web.dto.request.clubseasontable.RequestCreateClubSeasonTableDTO;
import vn.ptit.project.epl_web.dto.request.clubseasontable.RequestUpdateCstDTO;
import vn.ptit.project.epl_web.dto.response.clubseasontable.ResponseCreateClubSeasonTableDTO;
import vn.ptit.project.epl_web.service.ClubSeasonTableService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1/clubseasontables")
public class ClubSeasonTableController {
    private final ClubSeasonTableService clubSeasonTableService;

    public ClubSeasonTableController(ClubSeasonTableService clubSeasonTableService) {
        this.clubSeasonTableService = clubSeasonTableService;
    }
    @PostMapping("")
    @ApiMessage("Create a club season table")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateClubSeasonTableDTO> creatClubSeasonTable(RequestCreateClubSeasonTableDTO requestCreateClubSeasonTableDTO)
    {
        ClubSeasonTable clubSeasonTable=clubSeasonTableService.requestCreateClubSeasonTableDTOtoClubSeasonTable(requestCreateClubSeasonTableDTO);
        clubSeasonTableService.handleCreateClubSeasonTable( clubSeasonTable );
        return ResponseEntity.status(HttpStatus.CREATED).body(clubSeasonTableService.clubSeasonTabletoResponseCreateClubSeasonTableDTO(clubSeasonTable));
    }
    @PutMapping("")
    @ApiMessage("Update a club season table")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateClubSeasonTableDTO> updateClubSeasonTable(RequestUpdateCstDTO updateCstDTO){
        ClubSeasonTable clubSeasonTable=clubSeasonTableService.handleUpdateClubSeasonTable(updateCstDTO);
        return ResponseEntity.ok().body(clubSeasonTableService.clubSeasonTabletoResponseCreateClubSeasonTableDTO(clubSeasonTable));
    }
}
