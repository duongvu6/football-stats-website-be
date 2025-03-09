package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import vn.ptit.project.epl_web.domain.MatchAction;

public interface MatchActionRepository extends JpaRepository<MatchAction, Long> {
}
