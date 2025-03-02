package vn.ptit.project.epl_web.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.ptit.project.epl_web.domain.*;
import vn.ptit.project.epl_web.dto.request.league.RequestCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.request.league.RequestUpdateLeagueDTO;
import vn.ptit.project.epl_web.dto.request.player.RequestUpdatePlayerDTO;
import vn.ptit.project.epl_web.dto.request.transferhistory.RequestCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.dto.response.club.ResponseClubDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseUpdateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.leagueseason.ResponseCreateLeagueSeasonDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponsePlayerDTO;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        // Custom mapping for RequestUpdatePlayerDTO to Player
        mapper.addMappings(new PropertyMap<RequestUpdatePlayerDTO, Player>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getTransferHistories());
            }
        });
        mapper.addMappings(new PropertyMap<RequestCreateTransferHistoryDTO, TransferHistory>() {
            @Override
            protected void configure() {
                skip(destination.getPlayer());
                skip(destination.getClub());
            }
        });
        mapper.addMappings(new PropertyMap<TransferHistory, ResponseCreateTransferHistoryDTO>() {
            @Override
            protected void configure() {
                skip(destination.getPlayer());
                skip(destination.getClub());
            }
        });
        mapper.addMappings(new PropertyMap<Player, ResponsePlayerDTO>() {
            @Override
            protected void configure() {
                skip(destination.getTransferHistories());
            }
        });
        mapper.addMappings(new PropertyMap<RequestCreateLeagueDTO, League>() {

            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getLeagueSeasons());
            }
        });
        mapper.addMappings(new PropertyMap<League, ResponseCreateLeagueDTO>() {

            @Override
            protected void configure() {

            }
        });
        mapper.addMappings(new PropertyMap<RequestUpdateLeagueDTO, League>() {

            @Override
            protected void configure() {
                skip(destination.getLeagueSeasons());
            }
        });
        mapper.addMappings(new PropertyMap<LeagueSeason, ResponseCreateLeagueSeasonDTO>() {

            @Override
            protected void configure() {

            }
        });
        mapper.addMappings(new PropertyMap<Club, ResponseClubDTO>() {

            @Override
            protected void configure() {
                skip(destination.getTransferHistories());
            }
        });


        return mapper;
    }
}