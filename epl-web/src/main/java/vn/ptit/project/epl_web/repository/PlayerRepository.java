package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.ptit.project.epl_web.domain.Player;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {

    Optional<Player> findById(Long id);
    @Query("""
    SELECT DISTINCT p
    FROM Player p
    JOIN TransferHistory th ON th.player = p
    JOIN LeagueSeason ls ON ls.id = :seasonId
    WHERE th.club.id = :clubId
    AND th.date <= ls.endDate
    AND (th.type NOT IN ('End of contract', 'Retired', 'Contract Terminated') OR th.type IS NULL)
    AND NOT EXISTS (
        SELECT 1
        FROM TransferHistory th2
        WHERE th2.player = p
        AND th2.date > th.date
        AND th2.date <= ls.endDate
        AND (th2.club.id != :clubId OR th2.type IN ('End of contract', 'Retired', 'Contract Terminated'))
    )
""")
    List<Player> findSquadByClubAndSeason(@Param("clubId") Long clubId, @Param("seasonId") Long seasonId);
}
