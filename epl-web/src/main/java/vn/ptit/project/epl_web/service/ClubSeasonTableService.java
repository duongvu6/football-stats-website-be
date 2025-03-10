package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.ClubSeasonTable;
import vn.ptit.project.epl_web.dto.request.clubseasontable.RequestCreateClubSeasonTableDTO;
import vn.ptit.project.epl_web.dto.request.clubseasontable.RequestUpdateCstDTO;
import vn.ptit.project.epl_web.dto.response.clubseasontable.ResponseCreateClubSeasonTableDTO;
import vn.ptit.project.epl_web.dto.response.clubseasontable.ClubSeasonTablesDTO;
import vn.ptit.project.epl_web.repository.ClubSeasonTableRepository;

@Service
public class ClubSeasonTableService {
        private final ClubSeasonTableRepository clubSeasonTableRepository;
        private final ClubService clubService;
        private final LeagueSeasonService leagueSeasonService;
        private final ModelMapper modelMapper;
    public ClubSeasonTableService(ClubSeasonTableRepository clubSeasonTableRepository, ClubService clubService, @Lazy LeagueSeasonService leagueSeasonService, ModelMapper modelMapper) {
        this.clubSeasonTableRepository = clubSeasonTableRepository;
        this.clubService = clubService;
        this.leagueSeasonService = leagueSeasonService;
        this.modelMapper = modelMapper;
    }
    public ClubSeasonTablesDTO tableToClubSeasonTableDTO(ClubSeasonTable clubSeasonTable){
            ClubSeasonTablesDTO clubSeasonTablesDTO = modelMapper.map(clubSeasonTable, ClubSeasonTablesDTO.class);
            clubSeasonTablesDTO.setClubId(clubSeasonTable.getClub().getId());
            clubSeasonTablesDTO.setSeasonId(clubSeasonTable.getSeason().getId());
            return clubSeasonTablesDTO;
    }
    public ClubSeasonTable requestCreateClubSeasonTableDTOtoClubSeasonTable(RequestCreateClubSeasonTableDTO requestCreateClubSeasonTableDTO){
        ClubSeasonTable clubSeasonTable=modelMapper.map(requestCreateClubSeasonTableDTO, ClubSeasonTable.class);
        clubSeasonTable.setClub(clubService.getClubById(requestCreateClubSeasonTableDTO.getClubId()).get());
        clubSeasonTable.setSeason(leagueSeasonService.findByLeagueSeasonId(requestCreateClubSeasonTableDTO.getSeasonId()));
        return clubSeasonTable;
    }
    public ClubSeasonTable handleCreateClubSeasonTable(ClubSeasonTable clubSeasonTable){
        return clubSeasonTableRepository.save(clubSeasonTable);
    }
    public ResponseCreateClubSeasonTableDTO clubSeasonTabletoResponseCreateClubSeasonTableDTO(ClubSeasonTable clubSeasonTable){
        ResponseCreateClubSeasonTableDTO responseCreateClubSeasonTableDTO=modelMapper.map(clubSeasonTable, ResponseCreateClubSeasonTableDTO.class);
        responseCreateClubSeasonTableDTO.setClubId(clubSeasonTable.getClub().getId());
        responseCreateClubSeasonTableDTO.setSeasonId(clubSeasonTable.getSeason().getId());
        return responseCreateClubSeasonTableDTO;
    }
    public ClubSeasonTable handleUpdateClubSeasonTable(RequestUpdateCstDTO dto){
        ClubSeasonTable clubSeasonTable=modelMapper.map(dto, ClubSeasonTable.class);
        clubSeasonTable.setClub(clubService.getClubById(dto.getClubId()).get());
        clubSeasonTable.setSeason(leagueSeasonService.findByLeagueSeasonId(dto.getSeasonId()));
        return clubSeasonTableRepository.save(clubSeasonTable);
    }
}
