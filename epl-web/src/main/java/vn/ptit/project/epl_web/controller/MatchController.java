package vn.ptit.project.epl_web.controller;

import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.domain.Match;
import vn.ptit.project.epl_web.dto.request.match.RequestCreateMatchDTO;
import vn.ptit.project.epl_web.dto.request.match.RequestUpdateMatchDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.match.ResponseCreateMatchDTO;
import vn.ptit.project.epl_web.dto.response.match.ResponseUpdateMatchDTO;
import vn.ptit.project.epl_web.service.MatchService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }
    @PostMapping("")
    @ApiMessage("Create a match")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateMatchDTO> createMatch(@RequestBody  RequestCreateMatchDTO requestCreateMatchDTO)  {
        Match match=matchService.requestCreateMatchDTOtoMatch(requestCreateMatchDTO);
        matchService.handleCreateMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.matchToResponseCreateMatchDTO(match));
    }

    @PutMapping("")
    @ApiMessage("Update a match")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdateMatchDTO> updateMatch(@RequestBody RequestUpdateMatchDTO requestUpdateMatchDTO) throws InvalidRequestException {
        Match match=matchService.handleUpdateMatch(requestUpdateMatchDTO);
        return ResponseEntity.status(HttpStatus.OK).body(matchService.matchToResponseUpdateMatchDTO(match));
    }

    @GetMapping("{id}")
    @ApiMessage("Fetch a match")
    public ResponseEntity<ResponseUpdateMatchDTO> fetchMatch(@PathVariable Long id)  {
        Match match=matchService.findMatchById(id);
        return ResponseEntity.status(HttpStatus.OK).body(matchService.matchToResponseUpdateMatchDTO(match));
    }

    @GetMapping("")
    @ApiMessage("Fetch all matches")
    public ResponseEntity<ResultPaginationDTO> fetchAllMatches(@Filter Specification<Match> spec, Pageable pageable){
        ResultPaginationDTO resultPaginationDTO= matchService.fetchAllMatches(spec,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(resultPaginationDTO);
    }

    @DeleteMapping("{id}")
    @ApiMessage("Delete a match")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) throws InvalidRequestException {
        Match match=matchService.findMatchById(id);
        if (match==null) {
            throw new InvalidRequestException("Match with id = " + id + " not found.");
        }
        this.matchService.deleteMatchById(id);
        return ResponseEntity.ok(null);
    }
}
