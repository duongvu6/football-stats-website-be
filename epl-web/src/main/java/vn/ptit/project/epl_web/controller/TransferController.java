package vn.ptit.project.epl_web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.project.epl_web.dto.request.transferhistory.RequestCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.dto.request.transferhistory.RequestUpdateTransferHistoryDTO;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.service.TransferHistoryService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {
    private final TransferHistoryService transferHistoryService;

    public TransferController(TransferHistoryService transferHistoryService) {
        this.transferHistoryService = transferHistoryService;
    }

    @PostMapping
    @ApiMessage("Update Transfer History")
    public ResponseEntity<ResponseCreateTransferHistoryDTO> updateTransferHistory(@RequestBody RequestUpdateTransferHistoryDTO thDTO) throws InvalidRequestException {
        return ResponseEntity.ok(this.transferHistoryService.transferHistoryToResponseCreateTransferHistoryDTO(this.transferHistoryService.handleUpdateTransferHistory(this.transferHistoryService.requestUpdateTransferHistoryDTOtoTransferHistory(thDTO))));
    }
}
