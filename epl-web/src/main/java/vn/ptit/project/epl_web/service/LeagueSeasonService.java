package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.domain.ClubSeasonTable;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.domain.LeagueSeason;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.request.leagueseason.RequestUpdateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.clubseasontable.ClubSeasonTablesDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.LeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseUpdateLeaguesSeasonDTO;
import vn.ptit.project.epl_web.dto.response.topscorer.ResponseTopGoalScorerDTO;
import vn.ptit.project.epl_web.repository.ClubRepository;
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
   private final ClubRepository clubRepository;
   public LeagueSeasonService(LeagueSeasonRepository leagueSeasonRepository, ModelMapper modelMapper, LeagueService leagueService, ClubSeasonTableService clubSeasonTableService, ClubRepository clubRepository) {
        this.leagueSeasonRepository = leagueSeasonRepository;
        this.modelMapper = modelMapper;
       this.leagueService = leagueService;
       this.clubSeasonTableService = clubSeasonTableService;
       this.clubRepository = clubRepository;
   }
   public LeagueSeason findByLeagueSeasonId(Long leagueSeasonId) {
       return leagueSeasonRepository.findById(leagueSeasonId).orElse(null);
   }
   public LeagueSeason requestDTOtoLeagueSeason(RequestCreateLeagueSeasonDTO dto){
        LeagueSeason leagueSeason=modelMapper.map(dto, LeagueSeason.class);
        League league=leagueService.findByLeagueId(dto.getLeague());
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
   public LeagueSeasonDTO leagueSeasonToLeagueSeasonDTO(LeagueSeason leagueSeason) {
       return this.modelMapper.map(leagueSeason, LeagueSeasonDTO.class);
   }

   public LeagueSeason handleUpdateLeagueSeason(LeagueSeason leagueSeason,RequestUpdateLeagueSeasonDTO dto){
         modelMapper.map(dto, leagueSeason);
         League league=leagueService.findByLeagueId(dto.getLeague());
         leagueSeason.setLeague(league);
         return this.leagueSeasonRepository.save(leagueSeason);

   }
   public ResponseUpdateLeaguesSeasonDTO seasontoDTO(LeagueSeason leagueSeason){
       ResponseUpdateLeaguesSeasonDTO dto=modelMapper.map(leagueSeason, ResponseUpdateLeaguesSeasonDTO.class);
       dto.setLeague(leagueSeason.getLeague().getId());
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
    public ArrayList<ResponseTopGoalScorerDTO> getTopGoalScorers(Long seasonId) {
        return new ArrayList<>(leagueSeasonRepository.findTopGoalScorersBySeason(seasonId));

    }
    public ArrayList<ResponseTopGoalScorerDTO> getTopAssists(Long seasonId) {
        return new ArrayList<>(leagueSeasonRepository.findTopAssistsBySeason(seasonId));
    }
    public ArrayList<ResponseTopGoalScorerDTO> getTopYellowCards(Long seasonId) {
        return new ArrayList<>(leagueSeasonRepository.findTopYellowCardsBySeason(seasonId));
    }
    public ArrayList<ResponseTopGoalScorerDTO> getTopRedCards(Long seasonId) {
        return new ArrayList<>(leagueSeasonRepository.findTopRedCardsBySeason(seasonId));
    }
    public List<LeagueSeasonDTO> getLeagueSeasonsByClubId(Long clubId) {
        List<LeagueSeason> seasons = this.clubRepository.findLeagueSeasonsByClubId(clubId);

        return seasons.stream()
                .map(this::leagueSeasonToLeagueSeasonDTO)
                .collect(Collectors.toList());
    }
    public ArrayList<ResponseTopGoalScorerDTO> getTopGoalScorersByClub(Long seasonId, Long clubId) throws InvalidRequestException {
        // Get the club name for filtering
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new InvalidRequestException("Club with id = " + clubId + " not found"));
        String clubName = club.getName();

        // Get all top scorers for the season and filter by club
        List<ResponseTopGoalScorerDTO> allScorers = leagueSeasonRepository.findTopGoalScorersBySeason(seasonId);

        List<ResponseTopGoalScorerDTO> filteredScorers = allScorers.stream()
                .filter(scorer -> clubName.equals(scorer.getCurrentClub()))
                .toList();

        return new ArrayList<>(filteredScorers);
    }

    public ArrayList<ResponseTopGoalScorerDTO> getTopAssistsByClub(Long seasonId, Long clubId) throws InvalidRequestException {
        // Get the club name for filtering
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new InvalidRequestException("Club with id = " + clubId + " not found"));
        String clubName = club.getName();

        // Get all top assists for the season and filter by club
        List<ResponseTopGoalScorerDTO> allAssists = leagueSeasonRepository.findTopAssistsBySeason(seasonId);

        List<ResponseTopGoalScorerDTO> filteredAssists = allAssists.stream()
                .filter(assister -> clubName.equals(assister.getCurrentClub()))
                .toList();

        return new ArrayList<>(filteredAssists);
    }

    /**
     * Updates the rankings in a league season table based on sorting criteria:
     * 1. Points (descending)
     * 2. Goal difference (descending)
     * 3. Goals scored (descending)
     * 4. Club name (ascending)
     *
     * @param seasonId ID of the league season to update rankings for
     */
    public void updateLeagueTableRankings(Long seasonId) {
        LeagueSeason leagueSeason = findByLeagueSeasonId(seasonId);
        if (leagueSeason == null || leagueSeason.getClubSeasonTables() == null) {
            return;
        }
        
        List<ClubSeasonTable> tables = new ArrayList<>(leagueSeason.getClubSeasonTables());
        
        // Sort tables by points (desc), then goal diff (desc), then goals scored (desc), then club name (asc)
        tables.sort((table1, table2) -> {
            // First compare points (descending)
            int comparison = Integer.compare(table2.getPoints(), table1.getPoints());
            if (comparison != 0) {
                return comparison;
            }
            
            // If points are equal, compare goal difference (descending)
            comparison = Integer.compare(table2.getDiff(), table1.getDiff());
            if (comparison != 0) {
                return comparison;
            }
            
            // If goal difference is equal, compare goals scored (descending)
            comparison = Integer.compare(table2.getGoalScores(), table1.getGoalScores());
            if (comparison != 0) {
                return comparison;
            }
            
            // If goals scored are equal, compare club names (ascending)
            return table1.getClub().getName().compareTo(table2.getClub().getName());
        });
        
        // Update the ranks
        for (int i = 0; i < tables.size(); i++) {
            ClubSeasonTable table = tables.get(i);
            table.setRanked(i + 1); // Ranks start from 1
            clubSeasonTableService.handleUpdateClubSeasonTable(table);
        }
    }
}
