package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.ptit.project.epl_web.domain.CoachClub;
@Repository
public interface CoachClubRepository extends JpaRepository<CoachClub, Long> , JpaSpecificationExecutor<CoachClub>{

}
