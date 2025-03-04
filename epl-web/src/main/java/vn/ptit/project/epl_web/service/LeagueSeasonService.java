package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.domain.LeagueSeason;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestUpdateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseUpdateLeaguesSeasonDTO;
import vn.ptit.project.epl_web.repository.LeagueSeasonRepository;
@Service
public class LeagueSeasonService {
   private final LeagueSeasonRepository leagueSeasonRepository;
   private final ModelMapper modelMapper;
   private final LeagueService leagueService;
   public LeagueSeasonService(LeagueSeasonRepository leagueSeasonRepository, ModelMapper modelMapper, LeagueService leagueService) {
        this.leagueSeasonRepository = leagueSeasonRepository;
        this.modelMapper = modelMapper;
       this.leagueService = leagueService;
   }
   public LeagueSeason findByLeagueSeasonId(Long leagueSeasonId) {
       return leagueSeasonRepository.findById(leagueSeasonId).orElse(null);
   }
   public LeagueSeason requestDTOtoLeagueSeason(RequestCreateLeagueSeasonDTO dto){
        LeagueSeason leagueSeason=modelMapper.map(dto, LeagueSeason.class);
        League league=leagueService.findByLeagueId(dto.getLeagueId());
        leagueSeason.setLeague(league);
        return leagueSeason;
   }
   public LeagueSeason handleCreatLeagueSeason(LeagueSeason leagueSeason){
        return this.leagueSeasonRepository.save(leagueSeason);

   }
   public ResponseCreateLeagueSeasonDTO leagueSeasontoDTO(LeagueSeason leagueSeason)
   {
        ResponseCreateLeagueSeasonDTO dto=this.modelMapper.map(leagueSeason, ResponseCreateLeagueSeasonDTO.class);
        Long leagueId=leagueSeason.getLeague().getId();
        dto.setLeagueId(leagueId);
        return dto;
   }
   public LeagueSeason handleUpdateLeagueSeason(LeagueSeason leagueSeason,RequestUpdateLeagueSeasonDTO dto){
         modelMapper.map(dto, leagueSeason);
         League league=leagueService.findByLeagueId(dto.getLeagueId());
         leagueSeason.setLeague(league);
         return this.leagueSeasonRepository.save(leagueSeason);

   }
   public ResponseUpdateLeaguesSeasonDTO seasontoDTO(LeagueSeason leagueSeason){
       ResponseUpdateLeaguesSeasonDTO dto=modelMapper.map(leagueSeason, ResponseUpdateLeaguesSeasonDTO.class);
       dto.setLeagueId(leagueSeason.getLeague().getId());
       return dto;
   }
}
