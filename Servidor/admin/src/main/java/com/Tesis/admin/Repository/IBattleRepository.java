package com.Tesis.admin.Repository;

import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.ScoreDto;
import com.Tesis.admin.Models.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBattleRepository extends JpaRepository<Battle,Long> {

    @Query("SELECT new com.Tesis.admin.Dto.Battle.ScoreDto(" +
            "    concat(u1.firstName, ' ', u1.lastName) as name1, " +
            "    COUNT(CASE WHEN b.winner = u1.id THEN 1 END) as winne1, " +
            "    concat(u2.firstName, ' ', u2.lastName) as name2, " +
            "    COUNT(CASE WHEN b.winner = u2.id THEN 1 END) as winne1" +
            ") " +
            "FROM BattleParticipation bp1 " +
            "JOIN BattleParticipation bp2 ON bp1.id.battleId = bp2.id.battleId " +
            "JOIN Battle b ON bp1.id.battleId = b.idBattle " +
            "JOIN User u1 ON bp1.id.appuserId = u1.id " +
            "JOIN User u2 ON bp2.id.appuserId = u2.id " +
            "WHERE bp1.id.appuserId = ?1 " +
            "    AND bp2.id.appuserId = ?2 " +
            "GROUP BY name1, name2")
    ScoreDto getScore(Long playerOne, Long playerTwo);
}
