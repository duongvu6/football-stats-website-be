package vn.ptit.project.epl_web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import vn.ptit.project.epl_web.domain.MatchAction;

public interface MatchActionRepository extends JpaRepository<MatchAction, Long> {
    public Page<MatchAction> findAll(Specification<MatchAction> spec, Pageable pageable);
}
