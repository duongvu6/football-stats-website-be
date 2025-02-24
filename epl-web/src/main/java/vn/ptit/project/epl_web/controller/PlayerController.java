package vn.ptit.project.epl_web.controller;


import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.dto.request.player.RequestCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.request.player.RequestUpdatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponsePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseUpdatePlayerDTO;
import vn.ptit.project.epl_web.service.PlayerService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("")
    @ApiMessage("Create a new player")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreatePlayerDTO> createNewPlayer(@Valid @RequestBody RequestCreatePlayerDTO player) {
        Player newPlayer = this.playerService.handleCreatePlayer(this.playerService.requestPlayerDTOtoPlayer(player));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.playerService.playerToResponseCreatePlayerDTO(newPlayer));
    }

    @PutMapping("")
    @ApiMessage("Update a player")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdatePlayerDTO> updateAPlayer(@Valid @RequestBody RequestUpdatePlayerDTO playerDTO) throws InvalidRequestException {
        Optional<Player> player = this.playerService.getPlayerById(playerDTO.getId());
        if (player.isEmpty()) {
            throw new InvalidRequestException("Player with id = " + playerDTO.getId() + " not found.");
        }
        Player updatedPlayer = this.playerService.handleUpdatePlayer(player.get(), playerDTO);
        return ResponseEntity.ok().body(this.playerService.playerToResponseUpdatePlayerDTO(updatedPlayer));
    }
    @GetMapping("/{id}")
    @ApiMessage("Fetch a player")
    public ResponseEntity<ResponsePlayerDTO> fetchAPlayer(@PathVariable Long id) throws InvalidRequestException {
        Optional<Player> player = this.playerService.getPlayerById(id);
        if (player.isEmpty()) {
            throw new InvalidRequestException("Player with id = " + id + " not found.");
        }
        return ResponseEntity.ok(this.playerService.playerToResponsePlayerDTO(player.get()));
    }


    @GetMapping("")
    @ApiMessage("Fetch all players")
    public ResponseEntity<ResultPaginationDTO> fetchAllPlayers(
            @Filter Specification<Player> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.playerService.fetchAllPlayers(spec, pageable));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a player")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAPlayer(@PathVariable Long id) {
        this.playerService.handleDeletePlayer(id);
        return ResponseEntity.ok(null);
    }

}