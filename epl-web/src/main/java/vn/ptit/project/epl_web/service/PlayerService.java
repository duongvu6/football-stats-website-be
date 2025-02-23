package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    private ModelMapper mapper;
    public PlayerService(PlayerRepository playerRepository, ModelMapper mapper) {
        this.playerRepository = playerRepository;
        this.mapper = mapper;
    }

    public Player handleCreatePlayer(Player player) {
        // TODO Auto-generated method stub

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
            //delete all club related
            //TO-DO


        }


        this.playerRepository.deleteById(id);
    }

}