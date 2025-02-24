package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.project.epl_web.domain.TransferHistory;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {

}
