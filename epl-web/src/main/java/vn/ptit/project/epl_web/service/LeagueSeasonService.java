package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.ptit.project.epl_web.domain.ClubSeasonTable;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.domain.LeagueSeason;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestUpdateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ClubSeasonTablesDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseUpdateLeaguesSeasonDTO;
import vn.ptit.project.epl_web.repository.LeagueSeasonRepository;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueSeasonService {
   private final LeagueSeasonRepository leagueSeasonRepository;
   private final ModelMapper modelMapper;
   private final LeagueService leagueService;
   private final ClubSeasonTableService clubSeasonTableService;
   public LeagueSeasonService(LeagueSeasonRepository leagueSeasonRepository, ModelMapper modelMapper, LeagueService leagueService, ClubSeasonTableService clubSeasonTableService) {
        this.leagueSeasonRepository = leagueSeasonRepository;
        this.modelMapper = modelMapper;
       this.leagueService = leagueService;
       this.clubSeasonTableService = clubSeasonTableService;
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
        dto.setLeague(leagueId);
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
       List<ClubSeasonTablesDTO> dtoList=new ArrayList<>();
       for(ClubSeasonTable cs:leagueSeason.getClubSeasonTables()){
           dtoList.add(this.clubSeasonTableService.tableToClubSeasonTableDTO(cs));
       }
       dto.setClubSeasonTables(dtoList);
       return dto;
   }
    public ResultPaginationDTO fetchAllLeagueSeasons(Specification<LeagueSeason> spe, Pageable pageable) {
        Page<LeagueSeason> leagueSeasonPage=this.leagueSeasonRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(leagueSeasonPage.getTotalPages());
        meta.setTotal(leagueSeasonPage.getTotalElements());
        result.setMeta(meta);
        List<ResponseUpdateLeaguesSeasonDTO> list=leagueSeasonPage.getContent().stream().map(this::seasontoDTO).collect(Collectors.toList());
        result.setResult(list);
        return result;
    }
    public void deleteLeagueSeason(Long leagueSeasonId ) throws InvalidRequestException {
       this.leagueSeasonRepository.deleteById(leagueSeasonId);
    }
}
