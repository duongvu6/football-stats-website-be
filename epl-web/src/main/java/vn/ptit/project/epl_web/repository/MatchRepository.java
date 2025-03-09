package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.project.epl_web.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
