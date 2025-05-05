package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.ptit.project.epl_web.domain.LeagueSeason;
import vn.ptit.project.epl_web.dto.response.topscorer.ResponseTopGoalScorerDTO;

import java.util.List;

@Repository
public interface LeagueSeasonRepository extends JpaRepository<LeagueSeason,Long>{
    @Query("""
        SELECT new vn.ptit.project.epl_web.dto.response.topscorer.ResponseTopGoalScorerDTO(
                    ma.player.id, ma.player.name,
                    SUM(CASE WHEN ma.action = 'GOAL' THEN 1 ELSE 0 END) AS goals,
                    SUM(CASE WHEN ma.action = 'ASSIST' THEN 1 ELSE 0 END) AS assists,
                    (SELECT t.club.name\s
                     FROM TransferHistory t\s
                     WHERE t.player.id = ma.player.id\s
                     AND t.type NOT IN ('End of contract', 'Retired', 'Contract Terminated')\s
                     ORDER BY t.date DESC\s
                     LIMIT 1) AS currentClub,
                     SUM(CASE WHEN ma.action = 'YELLOW_CARD' THEN 1 ELSE 0 END) AS yellowCards,
                     SUM(CASE WHEN ma.action = 'RED_CARD' THEN 1 ELSE 0 END) AS redCards
                )
                FROM MatchAction ma
                WHERE ma.action IN ('GOAL', 'ASSIST', 'YELLOW_CARD', 'RED_CARD')  AND ma.match.season.id = :seasonId
                GROUP BY ma.player.id, ma.player.name
        ORDER BY goals DESC, assists DESC, ma.player.name ASC
    """)
    List<ResponseTopGoalScorerDTO> findTopGoalScorersBySeason(@Param("seasonId") Long seasonId);
    @Query("""
        SELECT new vn.ptit.project.epl_web.dto.response.topscorer.ResponseTopGoalScorerDTO(
                    ma.player.id, ma.player.name,
                    SUM(CASE WHEN ma.action = 'GOAL' THEN 1 ELSE 0 END) AS goals,
                    SUM(CASE WHEN ma.action = 'ASSIST' THEN 1 ELSE 0 END) AS assists,
                    (SELECT t.club.name\s
                     FROM TransferHistory t\s
                     WHERE t.player.id = ma.player.id\s
                     AND t.type NOT IN ('End of contract', 'Retired', 'Contract Terminated')\s
                     ORDER BY t.date DESC\s
                     LIMIT 1) AS currentClub,
                     SUM(CASE WHEN ma.action = 'YELLOW_CARD' THEN 1 ELSE 0 END) AS yellowCards,
                     SUM(CASE WHEN ma.action = 'RED_CARD' THEN 1 ELSE 0 END) AS redCards
                )
                FROM MatchAction ma
                WHERE ma.action IN ('GOAL', 'ASSIST', 'YELLOW_CARD', 'RED_CARD')  AND ma.match.season.id = :seasonId
                GROUP BY ma.player.id, ma.player.name
        ORDER BY assists DESC, goals DESC, ma.player.name ASC
    """)
    List<ResponseTopGoalScorerDTO> findTopAssistsBySeason(@Param("seasonId") Long seasonId);
    @Query("""
        SELECT new vn.ptit.project.epl_web.dto.response.topscorer.ResponseTopGoalScorerDTO(
                    ma.player.id, ma.player.name,
                    SUM(CASE WHEN ma.action = 'GOAL' THEN 1 ELSE 0 END) AS goals,
                    SUM(CASE WHEN ma.action = 'ASSIST' THEN 1 ELSE 0 END) AS assists,
                    (SELECT t.club.name\s
                     FROM TransferHistory t\s
                     WHERE t.player.id = ma.player.id\s
                     AND t.type NOT IN ('End of contract', 'Retired', 'Contract Terminated')\s
                     ORDER BY t.date DESC\s
                     LIMIT 1) AS currentClub,
                     SUM(CASE WHEN ma.action = 'YELLOW_CARD' THEN 1 ELSE 0 END) AS yellowCards,
                     SUM(CASE WHEN ma.action = 'RED_CARD' THEN 1 ELSE 0 END) AS redCards
                )
                FROM MatchAction ma
                WHERE ma.action IN ('GOAL', 'ASSIST', 'YELLOW_CARD', 'RED_CARD')  AND ma.match.season.id = :seasonId
                GROUP BY ma.player.id, ma.player.name
        ORDER BY yellowCards DESC, redCards DESC, ma.player.name ASC
    """)
    List<ResponseTopGoalScorerDTO> findTopYellowCardsBySeason(@Param("seasonId") Long seasonId);
    @Query("""
        SELECT new vn.ptit.project.epl_web.dto.response.topscorer.ResponseTopGoalScorerDTO(
                    ma.player.id, ma.player.name,
                    SUM(CASE WHEN ma.action = 'GOAL' THEN 1 ELSE 0 END) AS goals,
                    SUM(CASE WHEN ma.action = 'ASSIST' THEN 1 ELSE 0 END) AS assists,
                    (SELECT t.club.name\s
                     FROM TransferHistory t\s
                     WHERE t.player.id = ma.player.id\s
                     AND t.type NOT IN ('End of contract', 'Retired', 'Contract Terminated')\s
                     ORDER BY t.date DESC\s
                     LIMIT 1) AS currentClub,
                     SUM(CASE WHEN ma.action = 'YELLOW_CARD' THEN 1 ELSE 0 END) AS yellowCards,
                     SUM(CASE WHEN ma.action = 'RED_CARD' THEN 1 ELSE 0 END) AS redCards
                )
                FROM MatchAction ma
                WHERE ma.action IN ('GOAL', 'ASSIST', 'YELLOW_CARD', 'RED_CARD')  AND ma.match.season.id = :seasonId
                GROUP BY ma.player.id, ma.player.name
        ORDER BY redCards DESC, yellowCards DESC, ma.player.name ASC
    """)
    List<ResponseTopGoalScorerDTO> findTopRedCardsBySeason(@Param("seasonId") Long seasonId);
}
