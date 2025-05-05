package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.domain.CoachClub;
import vn.ptit.project.epl_web.domain.HeadCoach;
import vn.ptit.project.epl_web.dto.request.coachclub.RequestCreateCoachClubDTO;
import vn.ptit.project.epl_web.dto.request.coachclub.RequestUpdateCoachClubDTO;
import vn.ptit.project.epl_web.dto.response.coachclub.ResponseCoachClubDTO;
import vn.ptit.project.epl_web.repository.ClubRepository;
import vn.ptit.project.epl_web.repository.CoachClubRepository;
import vn.ptit.project.epl_web.repository.CoachRepository;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.Optional;

@Service
public class CoachClubService {
    private final CoachClubRepository coachClubRepository;
    private final ModelMapper mapper;
    private final CoachRepository coachRepository;
    private final ClubRepository clubRepository;

    public CoachClubService(CoachClubRepository coachClubRepository, ModelMapper mapper,
                            CoachRepository coachRepository, ClubRepository clubRepository) {
        this.coachClubRepository = coachClubRepository;
        this.mapper = mapper;
        this.coachRepository = coachRepository;
        this.clubRepository = clubRepository;
    }

    public CoachClub findById(Long id) {
        return coachClubRepository.findById(id).orElse(null);
    }

    public CoachClub toCoachClub(RequestCreateCoachClubDTO coachClubDTO) throws InvalidRequestException {
        CoachClub coachClub = this.mapper.map(coachClubDTO, CoachClub.class);
        Long coachId = coachClubDTO.getHeadCoach();
        Long clubId = coachClubDTO.getClub();
        Optional<HeadCoach> coach = this.coachRepository.findById(coachId);
        Optional<Club> club = Optional.empty();
        if (clubId != null) {
            club = this.clubRepository.findById(clubId);
        }
        return this.mapHeadCoachAndClub(coach, club, coachId, clubId, coachClub);
    }

    public CoachClub toCoachClub(RequestUpdateCoachClubDTO coachClubDTO) throws InvalidRequestException {
        CoachClub coachClub = this.mapper.map(coachClubDTO, CoachClub.class);
        Long coachId = coachClubDTO.getHeadCoach();
        Long clubId = coachClubDTO.getClub();
        Optional<HeadCoach> coach = this.coachRepository.findById(coachId);
        Optional<Club> club = Optional.empty();
        if (clubId != null) {
            club = this.clubRepository.findById(clubId);
        }
        return this.mapHeadCoachAndClub(coach, club, coachId, clubId, coachClub);
    }

    private CoachClub mapHeadCoachAndClub(Optional<HeadCoach> coach, Optional<Club> club, Long coachId, Long clubId, CoachClub coachClub) throws InvalidRequestException {
        if (coach.isEmpty()) {
            throw new InvalidRequestException("Head coach with id = " + coachId + " not found.");
        }

        coachClub.setHeadCoach(coach.get());

        if (clubId == null) {
            coachClub.setClub(null);
        } else if (club.isPresent()) {
            coachClub.setClub(club.get());
        } else {
            throw new InvalidRequestException("Club with id = " + clubId + " not found.");
        }

        return coachClub;
    }

    public CoachClub handleCreateCoachClub(CoachClub coachClub) {
        return this.coachClubRepository.save(coachClub);
    }

    public CoachClub handleUpdateCoachClub(CoachClub coachClub) {
        return this.coachClubRepository.save(coachClub);
    }

    public ResponseCoachClubDTO coachClubToResponseCoachClubDTO(CoachClub coachClub) {
        ResponseCoachClubDTO responseCoachClubDTO = this.mapper.map(coachClub, ResponseCoachClubDTO.class);
        responseCoachClubDTO.setHeadCoach(coachClub.getHeadCoach().getName());
        if (coachClub.getClub() != null) {
            responseCoachClubDTO.setClub(coachClub.getClub().getName());
        } else {
            responseCoachClubDTO.setClub("Without club");
        }
        return responseCoachClubDTO;
    }

    public void handleDeleteCoachClubWithId(Long id) throws InvalidRequestException {
        Optional<CoachClub> coachClub = this.coachClubRepository.findById(id);
        if (coachClub.isEmpty()) {
            throw new InvalidRequestException("Coach club with id = " + id + " not found. ");
        }
        CoachClub deletedCoachClub = coachClub.get();
        this.handleDeleteCoachClub(deletedCoachClub);
    }

    public void handleDeleteCoachClub(CoachClub deletedCoachClub) {
        deletedCoachClub.setClub(null);
        deletedCoachClub.setHeadCoach(null);
        this.coachClubRepository.delete(deletedCoachClub);
    }
}