package vn.ptit.project.epl_web.controller;


import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
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
    public ResponseEntity<ResponseCreatePlayerDTO> createNewPlayer(@RequestBody RequestCreatePlayerDTO player) {
        Player newPlayer = this.playerService.handleCreatePlayer(this.playerService.convertRequestCreatePlayerDTOtoPlayer(player));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.playerService.convertPlayerToResponseCreatePlayerDTO(newPlayer));
    }

    @PutMapping("")
    @ApiMessage("Update a player")
    public ResponseEntity<ResponseUpdatePlayerDTO> updateAPlayer(@Valid @RequestBody RequestUpdatePlayerDTO playerDTO) throws InvalidRequestException {
        Optional<Player> player = this.playerService.getPlayerById(playerDTO.getId());
        if (player.isEmpty()) {
            throw new InvalidRequestException("Player with id = " + playerDTO.getId() + " not found.");
        }
        Player updatedPlayer = this.playerService.handleUpdatePlayer(player.get(), playerDTO);
        return ResponseEntity.ok().body(this.playerService.convertPlayerToResponseUpdatePlayerDTO(updatedPlayer));
    }
    @GetMapping("/players/{id}")
    @ApiMessage("Fetch a player")
    public ResponseEntity<Player> fetchAPlayer(@PathVariable Long id) throws InvalidRequestException {
        Optional<Player> player = this.playerService.getPlayerById(id);
        if (player.isEmpty()) {
            throw new InvalidRequestException("Player with id = " + id + " not found.");
        }
        return ResponseEntity.ok(player.get());
    }

    @GetMapping("/players")
    @ApiMessage("Fetch all players")
    public ResponseEntity<ResultPaginationDTO> fetchAllPlayers(
            @Filter Specification<Player> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.playerService.fetchAllPlayers(spec, pageable));
    }

    @DeleteMapping("/players/{id}")
    @ApiMessage("Delete a player")
    public ResponseEntity<Void> deleteAPlayer(@PathVariable Long id) {
        this.playerService.handleDeletePlayer(id);
        return ResponseEntity.ok(null);
    }

}