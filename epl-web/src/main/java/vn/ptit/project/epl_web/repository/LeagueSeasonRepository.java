package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ptit.project.epl_web.domain.LeagueSeason;
@Repository
public interface LeagueSeasonRepository extends JpaRepository<LeagueSeason,Long>{
    
}
