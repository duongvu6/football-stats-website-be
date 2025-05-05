package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.project.epl_web.domain.TransferHistory;

import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    @Query("""
        SELECT th
        FROM TransferHistory th
        JOIN LeagueSeason ls ON th.date BETWEEN ls.startDate AND ls.endDate
        WHERE (th.club.id = :clubId OR th.player.id IN (
            SELECT p.id
            FROM Player p
            JOIN p.transferHistories th2
            WHERE th2.club.id = :clubId
        ))
        AND ls.id = :seasonId
    """)
    List<TransferHistory> findAllTransfersByClubAndSeason(@Param("clubId") Long clubId, @Param("seasonId") Long seasonId);
}
