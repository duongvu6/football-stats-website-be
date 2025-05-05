package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.project.epl_web.domain.HeadCoach;

public interface CoachRepository extends JpaRepository<HeadCoach, Long>, JpaSpecificationExecutor<HeadCoach> {
}
