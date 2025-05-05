package vn.ptit.project.epl_web.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.domain.TransferHistory;
import vn.ptit.project.epl_web.dto.request.player.RequestCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.request.player.RequestUpdatePlayerDTO;
import vn.ptit.project.epl_web.dto.request.transferhistory.RequestCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponsePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseUpdatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.repository.PlayerRepository;
import vn.ptit.project.epl_web.util.AgeUtil;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final ModelMapper mapper;
    private final TransferHistoryService transferHistoryService;
    public PlayerService(PlayerRepository playerRepository, ModelMapper mapper, TransferHistoryService transferHistoryService) {
        this.playerRepository = playerRepository;
        this.mapper = mapper;
        this.transferHistoryService = transferHistoryService;
    }

    public List<ResponsePlayerDTO> getSquadByClubAndSeason(Long clubId, Long seasonId) {
        List<Player> players = playerRepository.findSquadByClubAndSeason(clubId, seasonId);
        return players.stream()
                .map(this::playerToResponsePlayerDTO)
                .collect(Collectors.toList());
    }
    public Player handleCreatePlayer(Player player) {
        return this.playerRepository.save(player);
    }

    public Player requestPlayerDTOtoPlayer(RequestCreatePlayerDTO playerDTO) {
        return this.mapper.map(playerDTO, Player.class);
    }
    public ResponseCreatePlayerDTO playerToResponseCreatePlayerDTO(Player player) {
        ResponseCreatePlayerDTO playerDTO = this.mapper.map(player, ResponseCreatePlayerDTO.class);
        playerDTO.setAge(AgeUtil.calculateAge(player.getDob()));
        return playerDTO;
    }
    public ResponseUpdatePlayerDTO playerToResponseUpdatePlayerDTO(Player player) {
        ResponseUpdatePlayerDTO playerDTO = this.mapper.map(player, ResponseUpdatePlayerDTO.class);
        playerDTO.setAge(AgeUtil.calculateAge(player.getDob()));
        playerDTO.setTransferHistories(this.mapTransferHistoryFromPlayer(player));
        return playerDTO;
    }
    public Player handleUpdatePlayer(Player player, RequestUpdatePlayerDTO playerDTO) throws InvalidRequestException {
        this.mapper.map(playerDTO, player);
        return this.playerRepository.save(player);
    }
    @Transactional
    public Optional<Player> getPlayerById(Long id) {
        return this.playerRepository.findById(id);
    }


    public ResultPaginationDTO fetchAllPlayers(Specification<Player> spec, Pageable pageable) {
        Page<Player> pagePlayer = this.playerRepository.findAll(spec, pageable);
        ResultPaginationDTO result = setResultPaginationDTO(pageable, pagePlayer, spec);
        List<ResponsePlayerDTO> list = pagePlayer.getContent().stream()
                .map(this::playerToResponsePlayerDTO)
                .collect(Collectors.toList());
        result.setResult(list);
        return result;
    }

    @Transactional
    public ResponsePlayerDTO playerToResponsePlayerDTO(Player player) {
        ResponsePlayerDTO playerDTO = this.mapper.map(player, ResponsePlayerDTO.class);
        playerDTO.setTransferHistories(this.mapTransferHistoryFromPlayer(player));
        if (playerDTO.getTransferHistories().isEmpty()) {
            playerDTO.setCurrentClub("No club");
        } else {
            playerDTO.setCurrentClub(playerDTO.getTransferHistories().get(0).getClub());
        }
        playerDTO.setAge(AgeUtil.calculateAge(player.getDob()));
        return playerDTO;
    }
    public List<ResponseCreateTransferHistoryDTO> mapTransferHistoryFromPlayer(Player player) {
        List<TransferHistory> sortedTransferHistories = player.getTransferHistories().stream()
                .sorted(Comparator.comparing(TransferHistory::getDate).reversed())
                .toList();
        List<ResponseCreateTransferHistoryDTO> transferHistoriesDTOs = sortedTransferHistories.stream()
                .map(this.transferHistoryService::transferHistoryToResponseCreateTransferHistoryDTO)
                .toList();
        if (!transferHistoriesDTOs.isEmpty()) {
            transferHistoriesDTOs.get(transferHistoriesDTOs.size() - 1).setPreviousClub("-");
            for (int i = transferHistoriesDTOs.size() - 2; i >= 0; i--) {
                transferHistoriesDTOs.get(i).setPreviousClub(transferHistoriesDTOs.get(i + 1).getClub());
            }
        }
        return transferHistoriesDTOs;
    }
    public void handleDeletePlayer(Long id) throws InvalidRequestException {
        Optional<Player> player = this.playerRepository.findById(id);
        if (player.isPresent()) {
            Player deletedPlayer = player.get();
            for (TransferHistory th : deletedPlayer.getTransferHistories()) {
                this.transferHistoryService.handleDeleteTransferHistory(th.getId());
            }
            this.playerRepository.delete(deletedPlayer);
        } else {
            throw new InvalidRequestException("Player with id = " + id + " not found. ");
        }
    }

    public ResponsePlayerDTO playerToResponsePlayerDTOWithSortedTransferHistory(Player player) throws InvalidRequestException {
        ResponsePlayerDTO playerDTO = this.mapper.map(player, ResponsePlayerDTO.class);

        // Sort transfer histories by date in descending order
        List<TransferHistory> sortedTransferHistories = player.getTransferHistories().stream()
                .sorted(Comparator.comparing(TransferHistory::getDate).reversed())
                .toList();

        // Map to DTOs
        List<ResponseCreateTransferHistoryDTO> transferHistoriesDTOs = sortedTransferHistories.stream()
                .map(this.transferHistoryService::transferHistoryToResponseCreateTransferHistoryDTO)
                .collect(Collectors.toList());
        if (transferHistoriesDTOs.isEmpty()) {
            playerDTO.setCurrentClub("No club");
        } else {
            transferHistoriesDTOs.get(transferHistoriesDTOs.size() - 1).setPreviousClub("-");
            for (int i = transferHistoriesDTOs.size() - 2; i >= 0; i--) {
                transferHistoriesDTOs.get(i).setPreviousClub(transferHistoriesDTOs.get(i + 1).getClub());
            }
            playerDTO.setCurrentClub(transferHistoriesDTOs.get(0).getClub());
        }

        playerDTO.setTransferHistories(transferHistoriesDTOs);

        playerDTO.setAge(AgeUtil.calculateAge(player.getDob()));
        return playerDTO;
    }

    @Transactional
    public ResultPaginationDTO fetchAllPlayersWithSortedTransferHistories(Specification<Player> spec, Pageable pageable) {
        // Using existing pagination functionality
        Page<Player> pagePlayer = this.playerRepository.findAll(spec, pageable);
        ResultPaginationDTO result = setResultPaginationDTO(pageable, pagePlayer, spec);

        // For each player, sort their transfer histories
        List<ResponsePlayerDTO> list = pagePlayer.getContent().stream()
                .map(player -> {
                    ResponsePlayerDTO playerDTO = this.mapper.map(player, ResponsePlayerDTO.class);

                    // Sort transfer histories by date in descending order
                    List<TransferHistory> sortedTransferHistories = player.getTransferHistories().stream()
                            .sorted(Comparator.comparing(TransferHistory::getDate).reversed())
                            .toList();

                    // Map to DTOs
                    List<ResponseCreateTransferHistoryDTO> transferHistoriesDTOs = sortedTransferHistories.stream()
                            .map(this.transferHistoryService::transferHistoryToResponseCreateTransferHistoryDTO)
                            .collect(Collectors.toList());
                    if (transferHistoriesDTOs.isEmpty()) {
                        playerDTO.setCurrentClub("No club");
                    } else {
                        transferHistoriesDTOs.get(transferHistoriesDTOs.size() - 1).setPreviousClub("-");
                        for (int i = transferHistoriesDTOs.size() - 2; i >= 0; i--) {
                            transferHistoriesDTOs.get(i).setPreviousClub(transferHistoriesDTOs.get(i + 1).getClub());
                        }
                        playerDTO.setCurrentClub(transferHistoriesDTOs.get(0).getClub());
                    }
                    playerDTO.setTransferHistories(transferHistoriesDTOs);
                    playerDTO.setAge(AgeUtil.calculateAge(player.getDob()));
                    return playerDTO;
                })
                .collect(Collectors.toList());

        result.setResult(list);
        return result;
    }
    public ResultPaginationDTO setResultPaginationDTO (Pageable pageable, Page<Player>pagePlayer, Specification<Player> spec) {
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pagePlayer.getTotalPages());
        meta.setTotal(pagePlayer.getTotalElements());
        result.setMeta(meta);
        return result;
    }

}