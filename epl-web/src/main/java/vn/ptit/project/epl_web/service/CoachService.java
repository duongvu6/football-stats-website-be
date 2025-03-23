package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.*;
import vn.ptit.project.epl_web.dto.request.coach.RequestCreateCoachDTO;
import vn.ptit.project.epl_web.dto.request.coach.RequestUpdateCoachDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.coach.ClubDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCreateCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseUpdateCoachDTO;
import vn.ptit.project.epl_web.dto.response.coachclub.ResponseCoachClubDTO;
import vn.ptit.project.epl_web.repository.CoachRepository;
import vn.ptit.project.epl_web.util.AgeUtil;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoachService {
    private final CoachRepository coachRepository;
    private final ModelMapper modelMapper;
    private final CoachClubService coachClubService;
    private final ClubService clubService;

    public CoachService(CoachRepository coachRepository, ModelMapper modelMapper, CoachClubService coachClubService, ClubService clubService) {
        this.coachRepository = coachRepository;
        this.modelMapper = modelMapper;
        this.coachClubService = coachClubService;
        this.clubService = clubService;
    }

    public HeadCoach handleCreateCoach(HeadCoach coach) {
        return this.coachRepository.save(coach);
    }

    public HeadCoach requestCreateCoachDTOtoCoach(RequestCreateCoachDTO coachDTO) {
        return this.modelMapper.map(coachDTO, HeadCoach.class);
    }

    public ResponseCreateCoachDTO coachToResponseCreateCoachDTO(HeadCoach coach) {
        ResponseCreateCoachDTO coachDTO =  this.modelMapper.map(coach, ResponseCreateCoachDTO.class);
        coachDTO.setAge(AgeUtil.calculateAge(coach.getDob()));
        return coachDTO;
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
        List<ResponseCoachClubDTO> responseCoachClubDTOS = new ArrayList<>();
        List<CoachClub> coachClubs = coach.getCoachClubs();
        for (CoachClub coachClub : coachClubs) {
                responseCoachClubDTOS.add(this.coachClubService.coachClubToResponseCoachClubDTO(coachClub));
        }
        coachDTO.setCoachClubs(responseCoachClubDTOS);
        coachDTO.setAge(AgeUtil.calculateAge(coachDTO.getDob()));
        coachDTO.setCurrentClub(findCurrentClubByCoachId(coach));
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
        List<ResponseUpdateCoachDTO> list = coachPage.getContent().stream().map(this::coachToResponseUpdateCoachDTO).toList();
        result.setResult(list);
        return result;
    }

    public void handleDeleteCoach(Long id) throws InvalidRequestException {
        Optional<HeadCoach> coach = this.coachRepository.findById(id);
        if (coach.isPresent()) {
            HeadCoach deletedCoach = coach.get();
            //delete all club related
            for (CoachClub coachClub : deletedCoach.getCoachClubs()) {
                this.coachClubService.handleDeleteCoachClub(coachClub);
            }
            this.coachRepository.deleteById(id);
        } else {
            throw new InvalidRequestException("Head Coach with id = " + id  + " not found.");
        }

    }
    public ResponseCoachDTO coachToResponseCoachDTO(HeadCoach coach) {
        ResponseCoachDTO coachDTO = this.modelMapper.map(coach, ResponseCoachDTO.class);
        coachDTO.setAge(AgeUtil.calculateAge(coachDTO.getDob()));
        List<ResponseCoachClubDTO> responseCoachClubDTOS = new ArrayList<>();
        List<CoachClub> coachClubs = coach.getCoachClubs();
        for(CoachClub coachClub : coachClubs) {
            responseCoachClubDTOS.add(coachClubService.coachClubToResponseCoachClubDTO(coachClub));
        }
        coachDTO.setCoachClubs(responseCoachClubDTOS);
        coachDTO.setCurrentClub(findCurrentClubByCoachId(coach));
        return coachDTO;
    }
    public ResponseCoachDTO coachToResponseCoachWithSortedTransferHistory(HeadCoach coach) throws InvalidRequestException {
        ResponseCoachDTO coachDTO = this.modelMapper.map(coach, ResponseCoachDTO.class);

        List<CoachClub> sortedCoachClubs = coach.getCoachClubs().stream()
                .sorted(Comparator.comparing(CoachClub::getStartDate).reversed())
                .toList();

        // Map to DTOs
        List<ResponseCoachClubDTO> coachClubDTOs = sortedCoachClubs.stream()
                .map(this.coachClubService::coachClubToResponseCoachClubDTO)
                .toList();

        coachDTO.setCoachClubs(coachClubDTOs);
        coachDTO.setAge(AgeUtil.calculateAge(coach.getDob()));
        coachDTO.setCurrentClub(findCurrentClubByCoachId(coach));
        return coachDTO;
    }
    public ClubDTO findCurrentClubByCoachId(HeadCoach coach)
    {
//        List<CoachClub> coachClubList=new ArrayList<>();
//        for(CoachClub x: coach.getCoachClubs())
//        {
//            if(x.getHeadCoach().getId().equals(coach.getId()))
//            {
//                coachClubList.add(x);
//            }
//        }
        List<CoachClub> coachClubList = coach.getCoachClubs();

        List<CoachClub> sortedList = coachClubList.stream()
                .sorted(Comparator.comparing(CoachClub::getEndDate).reversed()) // Ngày gần nhất trước
                .collect(Collectors.toList());
        if(sortedList.isEmpty())
        {
            return null;
        }
        else
        {
            CoachClub coachClub = sortedList.get(0);
            Club club = coachClub.getClub();
            return modelMapper.map(club, ClubDTO.class);
        }


    }
}