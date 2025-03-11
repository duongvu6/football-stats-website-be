package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.Match;
import vn.ptit.project.epl_web.domain.MatchAction;
import vn.ptit.project.epl_web.dto.request.matchaction.RequestCreateMatchActionDTO;
import vn.ptit.project.epl_web.dto.request.matchaction.RequestUpdateMatchActionDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.match.MatchActionDTO;
import vn.ptit.project.epl_web.dto.response.matchaction.ResponseCreateMatchActionDTO;
import vn.ptit.project.epl_web.dto.response.matchaction.ResponseUpdateMatchActionDTO;
import vn.ptit.project.epl_web.repository.MatchActionRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MatchActionService {
    private final MatchActionRepository matchActionRepository;
    private final ModelMapper modelMapper;
    private final PlayerService playerService;
    private final MatchService matchService;
    public MatchActionService(MatchActionRepository matchActionRepository, ModelMapper modelMapper, PlayerService playerService, @Lazy MatchService matchService) {
        this.matchActionRepository = matchActionRepository;
        this.modelMapper = modelMapper;
        this.playerService = playerService;
        this.matchService = matchService;
    }
    public MatchActionDTO MatchActionToMatchActionDTO(MatchAction matchAction) {
        MatchActionDTO actionDTO=modelMapper.map(matchAction, MatchActionDTO.class);
        actionDTO.setPlayer(matchAction.getPlayer().getId());
        return actionDTO;
    }
    public MatchAction RequestCreateMatchActionDTOtoMatchAction(RequestCreateMatchActionDTO matchActionDTO) {
        MatchAction matchAction=modelMapper.map(matchActionDTO, MatchAction.class);
        matchAction.setPlayer(playerService.getPlayerById(matchActionDTO.getPlayer()).get());
        matchAction.setMatch(matchService.findMatchById(matchActionDTO.getMatch()));
        return matchAction;
    }
    public MatchAction handleCreateMatchAction(MatchAction matchAction) {
        return matchActionRepository.save(matchAction);
    }
    public ResponseCreateMatchActionDTO matchActionToResponseCreateMatchActionDTO(MatchAction matchAction) {
        ResponseCreateMatchActionDTO responseDTO=modelMapper.map(matchAction, ResponseCreateMatchActionDTO.class);
        responseDTO.setPlayer(matchAction.getPlayer().getId());
        responseDTO.setMatch(matchAction.getMatch().getId());
        return responseDTO;
    }
    public MatchAction handleUpdateMatchAction(RequestUpdateMatchActionDTO matchActionDTO) {
        MatchAction matchAction=modelMapper.map(matchActionDTO, MatchAction.class);
        matchAction.setPlayer(playerService.getPlayerById(matchActionDTO.getPlayer()).get());
        matchAction.setMatch(matchService.findMatchById(matchActionDTO.getMatch()));
        return matchActionRepository.save(matchAction);
    }
    public ResponseUpdateMatchActionDTO matchActionToResponseUpdateMatchActionDTO(MatchAction matchAction) {
        ResponseUpdateMatchActionDTO responseDTO=modelMapper.map(matchAction, ResponseUpdateMatchActionDTO.class);
        responseDTO.setPlayer(matchAction.getPlayer().getId());
        responseDTO.setMatch(matchAction.getMatch().getId());
        return responseDTO;
    }
    public MatchAction findMatchActionById(Long id) {
        return matchActionRepository.findById(id).get();
    }
    public ResultPaginationDTO fetchAllMatchActions(Specification<MatchAction> spec, Pageable pageable) {
        Page<MatchAction> matchActionPage = this.matchActionRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(matchActionPage.getTotalPages());
        meta.setTotal(matchActionPage.getTotalElements());
        result.setMeta(meta);
        List<ResponseUpdateMatchActionDTO> responseUpdateMatchActionDTOList = matchActionPage.getContent().stream().map(this::matchActionToResponseUpdateMatchActionDTO).collect(Collectors.toList());
        result.setResult(responseUpdateMatchActionDTOList);
        return result;
    }
    public void deleteMatchActionById(Long id) {
        matchActionRepository.deleteById(id);

    }

}
