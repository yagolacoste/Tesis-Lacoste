package com.Tesis.admin.Repository;

import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Models.Battle;
import com.Tesis.admin.Models.BattleParticipation;
import com.Tesis.admin.Models.BattleParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBattleParticipationRepository extends JpaRepository<BattleParticipation,BattleParticipationId> {
    @Query("SELECT DISTINCT b " +
            "from Battle b inner join BattleParticipation bp on (b.idBattle=bp.id.battleId) where bp.id.appuserId =:id")
    List<Battle> findByBattlesByUser(Long id);

    @Query("select new com.Tesis.admin.Dto.Statistics.StatisticsDto(s) from BattleParticipation bp inner join Statistics s on bp.statistics.id=s.id where bp.id.battleId=:id order by s.time,s.avgSpeed")//preg esto o comparable
    List<StatisticsDto> getRanking(Long id);
}
