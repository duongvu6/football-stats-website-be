package vn.ptit.project.epl_web.controller;

import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.MatchAction;
import vn.ptit.project.epl_web.dto.request.matchaction.RequestCreateMatchActionDTO;
import vn.ptit.project.epl_web.dto.request.matchaction.RequestUpdateMatchActionDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.match.MatchActionDTO;
import vn.ptit.project.epl_web.dto.response.matchaction.ResponseCreateMatchActionDTO;
import vn.ptit.project.epl_web.dto.response.matchaction.ResponseUpdateMatchActionDTO;
import vn.ptit.project.epl_web.service.MatchActionService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

@RestController
@RequestMapping("/api/v1/match_actions")
public class MatchActionController {
    private final MatchActionService matchActionService;

    public MatchActionController(MatchActionService matchActionService) {
        this.matchActionService = matchActionService;
    }
    @PostMapping("")
    @ApiMessage("Create a match action")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateMatchActionDTO> createMatchAction(RequestCreateMatchActionDTO requestCreateMatchActionDTO) throws InvalidRequestException {
        MatchAction matchAction=matchActionService.handleCreateMatchAction(matchActionService.RequestCreateMatchActionDTOtoMatchAction(requestCreateMatchActionDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(matchActionService.matchActionToResponseCreateMatchActionDTO(matchAction));
    }
    @PutMapping("")
    @ApiMessage("Update a match action")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdateMatchActionDTO> updateMatchAction(RequestUpdateMatchActionDTO requestUpdateMatchActionDTO) throws InvalidRequestException {
        MatchAction matchAction=matchActionService.handleUpdateMatchAction(requestUpdateMatchActionDTO);
        return ResponseEntity.ok(matchActionService.matchActionToResponseUpdateMatchActionDTO(matchAction));
    }
    @GetMapping("{id}")
    @ApiMessage("Fetch a match action")
    public ResponseEntity<ResponseUpdateMatchActionDTO> fetchMatchActionById(@PathVariable Long id) throws InvalidRequestException {
        MatchAction matchAction=matchActionService.findMatchActionById(id);
        if(matchAction==null){
            throw new InvalidRequestException("Match action not found");
        }
        return ResponseEntity.ok(matchActionService.matchActionToResponseUpdateMatchActionDTO(matchAction));
    }
    @GetMapping("")
    @ApiMessage("Fetch all match actions")
    public ResponseEntity<ResultPaginationDTO> fetchAllMatchActions(@Filter Specification<MatchAction> spec, Pageable pageable) throws InvalidRequestException {
        ResultPaginationDTO resultPaginationDTO=matchActionService.fetchAllMatchActions(spec, pageable);
        return ResponseEntity.ok(resultPaginationDTO);
    }
    @DeleteMapping("{id}")
    @ApiMessage("Delete a match action")
    public ResponseEntity<Void> deleteMatchAction(@PathVariable Long id) throws InvalidRequestException {
        MatchAction matchAction=matchActionService.findMatchActionById(id);
        if(matchAction==null){
            throw new InvalidRequestException("Match action not found");
        }
        matchActionService.deleteMatchActionById(id);
        return ResponseEntity.ok(null);
    }
}
