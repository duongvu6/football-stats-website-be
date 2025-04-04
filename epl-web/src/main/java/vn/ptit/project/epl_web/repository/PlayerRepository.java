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
        JOIN p.transferHistories th
        JOIN th.club c
        JOIN c.clubSeasonTables cst
        WHERE c.id = :clubId
          AND cst.season.id = :seasonId
          AND th.date <= cst.season.endDate
          AND (th.type NOT IN ('End of contract', 'Retired', 'Contract Terminated') OR th.type IS NULL)
          AND NOT EXISTS (
              SELECT 1
              FROM TransferHistory th2
              WHERE th2.player.id = p.id
                AND th2.date <= cst.season.endDate
                AND th2.date >= cst.season.startDate
                AND th2.club.id != :clubId
          )
    """)
    List<Player> findSquadByClubAndSeason(@Param("clubId") Long clubId, @Param("seasonId") Long seasonId);
}
