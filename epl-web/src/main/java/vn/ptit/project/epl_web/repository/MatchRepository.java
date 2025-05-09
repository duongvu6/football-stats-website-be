package vn.ptit.project.epl_web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.domain.Match;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    public List<Match> findByHost(Club host);
    public List<Match> findByAway(Club away);
    public Page<Match> findAll(Specification<Match> spec, Pageable pageable);
}
