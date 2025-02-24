package vn.ptit.project.epl_web.service;

import org.apache.coyote.Request;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.domain.TransferHistory;
import vn.ptit.project.epl_web.dto.request.transferhistory.RequestCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.repository.ClubRepository;
import vn.ptit.project.epl_web.repository.PlayerRepository;
import vn.ptit.project.epl_web.repository.TransferHistoryRepository;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransferHistoryService {
    private final TransferHistoryRepository repository;
    private final ModelMapper mapper;
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;

    public TransferHistoryService(TransferHistoryRepository repository, ModelMapper mapper, PlayerRepository playerRepository, ClubRepository clubRepository)
    {
        this.repository = repository;
        this.mapper = mapper;
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
    }
    public TransferHistory createTransferHistory(RequestCreateTransferHistoryDTO dto) throws InvalidRequestException {
        return this.repository.save(this.requestCreateTransferHistoryDTOtoTransferHistory(dto));
    }
    public ResponseCreateTransferHistoryDTO transferHistoryToResponseCreateTransferHistoryDTO(TransferHistory th) {
        ResponseCreateTransferHistoryDTO dto = this.mapper.map(th, ResponseCreateTransferHistoryDTO.class);
        dto.setPlayer(th.getPlayer().getName());
        dto.setClub(th.getClub().getName());
        return dto;
    }
    public TransferHistory requestCreateTransferHistoryDTOtoTransferHistory(RequestCreateTransferHistoryDTO thDTO) throws InvalidRequestException {
        TransferHistory transferHistory = this.mapper.map(thDTO, TransferHistory.class);
        Optional<Player> player = this.playerRepository.findById(thDTO.getPlayer());
        Optional<Club> club = this.clubRepository.findById(thDTO.getClub());
        if (player.isPresent()) {
            transferHistory.setPlayer(player.get());
        } else {
            throw new InvalidRequestException("Player with id = " + thDTO.getPlayer() + " not found.");
        }
        if (club.isPresent()) {
            transferHistory.setClub(club.get());
        } else {
            throw new InvalidRequestException("Club with id = " + thDTO.getClub() + " not found.");
        }
        return transferHistory;
    }



}
