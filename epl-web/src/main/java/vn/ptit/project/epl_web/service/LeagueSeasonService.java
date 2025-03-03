package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import vn.ptit.project.epl_web.domain.LeagueSeason;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.repository.LeagueSeasonRepository;
@Service
public class LeagueSeasonService {
   private final LeagueSeasonRepository leagueSeasonRepository;
   private final ModelMapper modelMapper;
   public LeagueSeasonService(LeagueSeasonRepository leagueSeasonRepository, ModelMapper modelMapper) {
        this.leagueSeasonRepository = leagueSeasonRepository;
        this.modelMapper = modelMapper;
}

   public LeagueSeason requestDTOtoLeagueSeason(RequestCreateLeagueSeasonDTO dto){
        return this.modelMapper.map(dto, LeagueSeason.class);
   }
   public LeagueSeason handleCreatLeagueSeason(LeagueSeason leagueSeason){
        return this.leagueSeasonRepository.save(leagueSeason);

   }
   public ResponseCreateLeagueSeasonDTO leagueSeasontoDTO(LeagueSeason leagueSeason)
   {
    return this.modelMapper.map(leagueSeason, ResponseCreateLeagueSeasonDTO.class);
   }
}
