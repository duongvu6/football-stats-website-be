package vn.ptit.project.epl_web.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.dto.request.player.RequestUpdatePlayerDTO;

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

        return mapper;
    }
}