package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.*;
import vn.ptit.project.epl_web.dto.request.club.RequestCreateClubDTO;
import vn.ptit.project.epl_web.dto.request.club.RequestUpdateClubDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.club.*;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.repository.ClubRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;
    private final TransferHistoryService transferHistoryService;

    public ClubService(ClubRepository clubRepository, ModelMapper modelMapper, TransferHistoryService transferHistoryService) {
        this.clubRepository = clubRepository;
        this.modelMapper = modelMapper;
        this.transferHistoryService = transferHistoryService;
    }

    public Club handleCreateClub(Club club) {
        return this.clubRepository.save(club);
    }
    public Club requestCreateClubToClub(RequestCreateClubDTO clubDTO) {
        return this.modelMapper.map(clubDTO, Club.class);
    }
    public ResponseCreateClubDTO clubToResponseCreateClubDTO(Club club) {
        return this.modelMapper.map(club, ResponseCreateClubDTO.class);
    }
    public Optional<Club> getClubById(Long id) {
        return this.clubRepository.findById(id);
    }
    public Club handleUpdateClub(Club club, RequestUpdateClubDTO clubDTO) {
//        if (clubDTO.getCoachHistory() != null) {
//            //TO-DO
//        }
//        if (clubDTO.getPlayerHistory() != null) {
//            //TO-DO
//        }
//        club = this.modelMapper.map(club, clubDTO);
//        return this.clubRepository.save(club);
        //TODO finish function
        return null;
    }
    public ResponseUpdateClubDTO clubToResponseUpdateClub(Club club) {
        return this.modelMapper.map(club, ResponseUpdateClubDTO.class);

    }
    public ResultPaginationDTO fetchAllClubs(Specification<Club> spec, Pageable pageable) {
        Page<Club> clubPage = this.clubRepository.findAll(spec, pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(clubPage.getTotalPages());
        meta.setTotal(clubPage.getTotalElements());
        result.setMeta(meta);
        List<ResponseClubDTO> list = clubPage.getContent().stream().map(this::clubToResponseClubDTO).toList();
        result.setResult(list);
        return result;
    }
    public void handleDeleteClub(Long id) {
        Optional<Club> club = this.clubRepository.findById(id);
        if (club.isPresent()) {
            Club deletedClub = club.get();
            //delete all club related
            //TO-DO

            //delete all player positions
            //TO-DO
        }


        this.clubRepository.deleteById(id);
    }

    public ResponseClubDTO clubToResponseClubDTO(Club club) {
        ResponseClubDTO clubDTO = this.modelMapper.map(club, ResponseClubDTO.class);
        List<ResponseCreateTransferHistoryDTO> transferHistories = new ArrayList<>();
        for (TransferHistory th : club.getTransferHistories()) {
            transferHistories.add(this.transferHistoryService.transferHistoryToResponseCreateTransferHistoryDTO(th));
        }
        clubDTO.setTransferHistories(transferHistories);
        clubDTO.setCurrentCoach(findCurrentCoachByClub(club));
        return clubDTO;
    }
    public CoachDTO findCurrentCoachByClub(Club club)
    {
        List<CoachClub> coachClubList=club.getCoachClubs();
        List<CoachClub> sortedList = coachClubList.stream()
                .sorted(Comparator.comparing(CoachClub::getEndDate).reversed()) // Ngày gần nhất trước
                .collect(Collectors.toList());
        if(sortedList.isEmpty())
        {
            return null;
        }
        else
        {
            CoachClub coachClub=sortedList.get(0);
            HeadCoach coach=coachClub.getHeadCoach();
            return modelMapper.map(coach, CoachDTO.class);
        }

    }
    public boolean playFor(Player player, Club club,LeagueSeason season)
    {
        List<TransferHistory> transferHistories = player.getTransferHistories();
        List<TransferHistory> filteredSortedHistories = transferHistories.stream()
                .filter(th -> !th.getDate().isBefore(season.getStartDate()) && !th.getDate().isAfter(season.getEndDate()))
                .sorted((th1, th2) -> th2.getDate().compareTo(th1.getDate())) // Sắp xếp giảm dần
                .toList();



    }
    public List<PlayerDTO> findPlayersByClub(Club club,LeagueSeason season)
    {
        List<TransferHistory> transferHistories = club.getTransferHistories();
        List<PlayerDTO> currentPlayerList = new ArrayList<>();
        for(TransferHistory th: transferHistories)
        {
            if(th.getType().equals("Permanent")||th.getType().equals("Free Transfer")||th.getType().equals("Loan")||th.getType().equals("Youth Promote"))
            {
                Player player =th.getPlayer();
                if(playFor(player,club,season)&&!transferHistories.contains(th))
                {
                    currentPlayerList.add(modelMapper.map(player, PlayerDTO.class));
                }
            }
        }
        return currentPlayerList;
    }
}
