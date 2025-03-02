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
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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


    public Player handleCreatePlayer(Player player) {
        return this.playerRepository.save(player);
    }

    public Player requestPlayerDTOtoPlayer(RequestCreatePlayerDTO playerDTO) {
        return this.mapper.map(playerDTO, Player.class);
    }
    public ResponseCreatePlayerDTO playerToResponseCreatePlayerDTO(Player player) {
        ResponseCreatePlayerDTO playerDTO = this.mapper.map(player, ResponseCreatePlayerDTO.class);
        playerDTO.setAge(PlayerService.calculateAge(player.getDob()));
        return playerDTO;
    }
    public ResponseUpdatePlayerDTO playerToResponseUpdatePlayerDTO(Player player) {
        ResponseUpdatePlayerDTO playerDTO = this.mapper.map(player, ResponseUpdatePlayerDTO.class);
        playerDTO.setAge(PlayerService.calculateAge(player.getDob()));
        playerDTO.setTransferHistories(this.mapTransferHistoryFromPlayer(player));
        return playerDTO;
    }
    public Player handleUpdatePlayer(Player player, RequestUpdatePlayerDTO playerDTO) throws InvalidRequestException {
        this.mapper.map(playerDTO, player);
        //handle transfer history
        if (playerDTO.getTransferHistories() != null) {
            //TODO: implement transfer history created and query to db
            // lấy transferhistory dto từ cầu thủ (là thuộc tính cua cau thu)
            List<RequestCreateTransferHistoryDTO> transferHistories = new ArrayList<>(playerDTO.getTransferHistories());
            // tao moi list cua transferhistory tao ra
            //List<TransferHistory> createdTransferHistories = new ArrayList<>();
            // tao transferhistory
            for (RequestCreateTransferHistoryDTO dto : transferHistories) {
                this.transferHistoryService.createTransferHistory(dto);
            }
//            player.setTransferHistories(new HashSet<>(createdTransferHistories));
        }
//        System.out.println(player);
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
        playerDTO.setAge(PlayerService.calculateAge(player.getDob()));
        return playerDTO;
    }
    public List<ResponseCreateTransferHistoryDTO> mapTransferHistoryFromPlayer(Player player) {
        List<TransferHistory> transferHistoryList = player.getTransferHistories();
        List<ResponseCreateTransferHistoryDTO> transferHistories = new ArrayList<>();
        for (TransferHistory th : transferHistoryList) {
            ResponseCreateTransferHistoryDTO newThDTO = this.transferHistoryService.transferHistoryToResponseCreateTransferHistoryDTO(th);
            transferHistories.add(newThDTO);
        }
        return transferHistories;
    }
    public void handleDeletePlayer(Long id) {
        Optional<Player> player = this.playerRepository.findById(id);
        if (player.isPresent()) {
            Player deletedPlayer = player.get();
            //TODO delete all club related


        }


        this.playerRepository.deleteById(id);
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

        playerDTO.setTransferHistories(transferHistoriesDTOs);
        playerDTO.setAge(PlayerService.calculateAge(player.getDob()));
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

                    playerDTO.setTransferHistories(transferHistoriesDTOs);
                    playerDTO.setAge(PlayerService.calculateAge(player.getDob()));
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
    public static int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();

        // Calculate the period between the two dates
        Period period = Period.between(dob, currentDate);
        return period.getYears(); // Get the number of years
    }
}