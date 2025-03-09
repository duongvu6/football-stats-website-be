package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.MatchAction;
import vn.ptit.project.epl_web.dto.response.match.MatchActionDTO;
import vn.ptit.project.epl_web.repository.MatchActionRepository;


@Service
public class MatchActionService {
    private final MatchActionRepository matchActionRepository;
    private final ModelMapper modelMapper;
    public MatchActionService(MatchActionRepository matchActionRepository, ModelMapper modelMapper) {
        this.matchActionRepository = matchActionRepository;
        this.modelMapper = modelMapper;
    }
    public MatchActionDTO MatchActionToMatchActionDTO(MatchAction matchAction) {
        MatchActionDTO actionDTO=modelMapper.map(matchAction, MatchActionDTO.class);
        actionDTO.setPlayerId(matchAction.getPlayer().getId());
        return actionDTO;
    }
}
