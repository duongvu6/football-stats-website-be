package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.dto.request.club.RequestCreateClubDTO;
import vn.ptit.project.epl_web.dto.request.club.RequestUpdateClubDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.club.ResponseCreateClubDTO;
import vn.ptit.project.epl_web.dto.response.club.ResponseUpdateClubDTO;
import vn.ptit.project.epl_web.repository.ClubRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;

    public ClubService(ClubRepository clubRepository, ModelMapper modelMapper) {
        this.clubRepository = clubRepository;
        this.modelMapper = modelMapper;
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
        Page<Club> clubPage = this.clubRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(clubPage.getTotalPages());
        meta.setTotal(clubPage.getTotalElements());
        result.setMeta(meta);
        List<Club> list = new ArrayList<>(clubPage.getContent());
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
}
