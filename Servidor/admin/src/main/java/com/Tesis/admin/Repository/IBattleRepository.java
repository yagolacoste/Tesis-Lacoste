package com.Tesis.admin.Repository;

import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Models.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBattleRepository extends JpaRepository<Battle,Long> {

}
