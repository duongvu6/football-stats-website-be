package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.dto.request.player.RequestCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.request.player.RequestUpdatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseUpdatePlayerDTO;
import vn.ptit.project.epl_web.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final ModelMapper mapper;
    public PlayerService(PlayerRepository playerRepository, ModelMapper mapper) {
        this.playerRepository = playerRepository;
        this.mapper = mapper;
    }

    public Player handleCreatePlayer(Player player) {
        return this.playerRepository.save(player);
    }

    public Player requestPlayerDTOtoPlayer(RequestCreatePlayerDTO playerDTO) {
        return this.mapper.map(playerDTO, Player.class);
    }
    public ResponseCreatePlayerDTO playerToResponseCreatePlayerDTO(Player player) {
        return this.mapper.map(player, ResponseCreatePlayerDTO.class);
    }
    public ResponseUpdatePlayerDTO playerToResponseUpdatePlayerDTO(Player player) {
        return this.mapper.map(player, ResponseUpdatePlayerDTO.class);
    }
    public Player handleUpdatePlayer(Player player, RequestUpdatePlayerDTO playerDTO) {
        this.mapper.map(playerDTO, player);
        //handle transfer history
        if (playerDTO.getTransferHistories() != null) {
            //TODO: implement transfer history created and query to db
        }
        return this.playerRepository.save(player);
    }
    public Optional<Player> getPlayerById(Long id) {
        return this.playerRepository.findById(id);
    }


    public ResultPaginationDTO fetchAllPlayers(Specification<Player> spec, Pageable pageable) {
        Page<Player> pagePlayer = this.playerRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pagePlayer.getTotalPages());
        meta.setTotal(pagePlayer.getTotalElements());
        result.setMeta(meta);
        List<Player> list = new ArrayList<>(pagePlayer.getContent());
        result.setResult(list);
        return result;
    }
    public void handleDeletePlayer(Long id) {
        Optional<Player> player = this.playerRepository.findById(id);
        if (player.isPresent()) {
            Player deletedPlayer = player.get();
            //TODO delete all club related


        }


        this.playerRepository.deleteById(id);
    }

}