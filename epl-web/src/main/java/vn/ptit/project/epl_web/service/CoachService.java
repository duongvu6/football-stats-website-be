package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.CoachClub;
import vn.ptit.project.epl_web.domain.HeadCoach;
import vn.ptit.project.epl_web.dto.request.coach.RequestCreateCoachDTO;
import vn.ptit.project.epl_web.dto.request.coach.RequestUpdateCoachDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCreateCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseUpdateCoachDTO;
import vn.ptit.project.epl_web.dto.response.coachclub.ResponseCreateCoachClubDTO;
import vn.ptit.project.epl_web.repository.CoachRepository;
import vn.ptit.project.epl_web.util.AgeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoachService {
    private final CoachRepository coachRepository;
    private final ModelMapper modelMapper;
    private final CoachClubService coachClubService;

    public CoachService(CoachRepository coachRepository, ModelMapper modelMapper, CoachClubService coachClubService) {
        this.coachRepository = coachRepository;
        this.modelMapper = modelMapper;
        this.coachClubService = coachClubService;
    }

    public HeadCoach handleCreateCoach(HeadCoach coach) {
        return this.coachRepository.save(coach);
    }

    public HeadCoach requestCreateCoachDTOtoCoach(RequestCreateCoachDTO coachDTO) {
        return this.modelMapper.map(coachDTO, HeadCoach.class);
    }

    public ResponseCreateCoachDTO coachToResponseCreateCoachDTO(HeadCoach coach) {
        return this.modelMapper.map(coach, ResponseCreateCoachDTO.class);
    }

    public Optional<HeadCoach> getCoachById(Long id) {
        return this.coachRepository.findById(id);
    }

    public HeadCoach handleUpdateCoach(HeadCoach coach, RequestUpdateCoachDTO coachDTO) {
        this.modelMapper.map(coachDTO, coach);
        return this.coachRepository.save(coach);
    }

    public ResponseUpdateCoachDTO coachToResponseUpdateCoachDTO(HeadCoach coach) {
        ResponseUpdateCoachDTO coachDTO= this.modelMapper.map(coach, ResponseUpdateCoachDTO.class);
        List<ResponseCreateCoachClubDTO> coachClubDTOs = new ArrayList<>();
        List<CoachClub> coachClubs = coach.getCoachClubs();
        for (CoachClub coachClub : coachClubs) {
                coachClubDTOs.add(this.coachClubService.coachClubToCreateCoachClubDTO(coachClub));
        }
        coachDTO.setCoachClubs(coachClubDTOs);
        coachDTO.setAge(AgeUtil.calculateAge(coachDTO.getDob()));
        return coachDTO;
    }

    public ResultPaginationDTO fetchAllCoaches(Specification<HeadCoach> spec, Pageable pageable) {
        Page<HeadCoach> coachPage = this.coachRepository.findAll(spec, pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(coachPage.getTotalPages());
        meta.setTotal(coachPage.getTotalElements());
        result.setMeta(meta);
        List<HeadCoach> list = new ArrayList<>(coachPage.getContent());
        result.setResult(list);
        return result;
    }

    public void handleDeleteCoach(Long id) {
        Optional<HeadCoach> coach = this.coachRepository.findById(id);
        if (coach.isPresent()) {
            HeadCoach deletedCoach = coach.get();
            //delete all club related
            //TODO handle delete all related

        }


        this.coachRepository.deleteById(id);
    }
    public ResponseCoachDTO coachToResponseCoachDTO(HeadCoach coach) {
        ResponseCoachDTO coachDTO = this.modelMapper.map(coach, ResponseCoachDTO.class);
        coachDTO.setAge(AgeUtil.calculateAge(coachDTO.getDob()));
        List<ResponseCreateCoachClubDTO> coachClubDTOs = new ArrayList<>();
        List<CoachClub> coachClubs = coach.getCoachClubs();
        for(CoachClub coachClub : coachClubs) {
            coachClubDTOs.add(coachClubService.coachClubToCreateCoachClubDTO(coachClub));
        }
        coachDTO.setCoachClubs(coachClubDTOs);
        return coachDTO;
    }
}