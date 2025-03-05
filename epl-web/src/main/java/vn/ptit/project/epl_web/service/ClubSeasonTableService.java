package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.ClubSeasonTable;
import vn.ptit.project.epl_web.dto.response.leagueseason.ClubSeasonTablesDTO;
import vn.ptit.project.epl_web.repository.ClubSeasonTableRepository;

@Service
public class ClubSeasonTableService {
        private final ClubSeasonTableRepository clubSeasonTableRepository;
        private final ModelMapper modelMapper;
    public ClubSeasonTableService(ClubSeasonTableRepository clubSeasonTableRepository, ModelMapper modelMapper) {
        this.clubSeasonTableRepository = clubSeasonTableRepository;
        this.modelMapper = modelMapper;
    }
    public ClubSeasonTablesDTO tableToClubSeasonTableDTO(ClubSeasonTable clubSeasonTable){
            ClubSeasonTablesDTO clubSeasonTablesDTO = modelMapper.map(clubSeasonTable, ClubSeasonTablesDTO.class);
            clubSeasonTablesDTO.setClubId(clubSeasonTable.getClub().getId());
            clubSeasonTablesDTO.setSeasonId(clubSeasonTable.getSeason().getId());
            return clubSeasonTablesDTO;
        }
}
