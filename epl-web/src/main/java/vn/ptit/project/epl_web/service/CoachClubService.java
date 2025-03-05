package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import vn.ptit.project.epl_web.domain.CoachClub;
import vn.ptit.project.epl_web.dto.response.coachclub.ResponseCreateCoachClubDTO;
import vn.ptit.project.epl_web.repository.CoachClubRepository;
import vn.ptit.project.epl_web.repository.CoachRepository;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.Optional;

@Service
public class CoachClubService {
    private final CoachClubRepository coachClubRepository;
    private final ModelMapper mapper;
    private final CoachRepository coachRepository;

    public CoachClubService(CoachClubRepository coachClubRepository, ModelMapper mapper,
            CoachRepository coachRepository) {
        this.coachClubRepository = coachClubRepository;
        this.mapper = mapper;
        this.coachRepository = coachRepository;
    }
    public CoachClub findById(Long id) {
        return coachClubRepository.findById(id).orElse(null);
    }
    public ResponseCreateCoachClubDTO coachClubToCreateCoachClubDTO(CoachClub coachClub) {
        ResponseCreateCoachClubDTO responseCreateCoachClubDTO = this.mapper.map(coachClub, ResponseCreateCoachClubDTO.class);
        responseCreateCoachClubDTO.setHeadCoach(coachClub.getHeadCoach().getName());
        responseCreateCoachClubDTO.setClub(coachClub.getClub().getName());
        return responseCreateCoachClubDTO;

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
