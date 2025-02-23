package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.HeadCoach;
import vn.ptit.project.epl_web.dto.request.coach.RequestCreateCoachDTO;
import vn.ptit.project.epl_web.dto.request.coach.RequestUpdateCoachDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCreateCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseUpdateCoachDTO;
import vn.ptit.project.epl_web.repository.CoachRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoachService {
    private final CoachRepository coachRepository;
    private final ModelMapper modelMapper;

    public CoachService(CoachRepository coachRepository, ModelMapper modelMapper) {
        this.coachRepository = coachRepository;
        this.modelMapper = modelMapper;
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
//        if (coachDTO.getClubHistory() != null) {
//            //TO-DO
//        }
        //TODO handle dto
        return null;

    }

    public ResponseUpdateCoachDTO coachToResponseUpdateCoachDTO(HeadCoach coach) {
        return this.modelMapper.map(coach, ResponseUpdateCoachDTO.class);
    }

    public ResultPaginationDTO fetchAllCoaches(Specification<HeadCoach> spec, Pageable pageable) {
        Page<HeadCoach> coachPage = this.coachRepository.findAll(pageable);
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
}