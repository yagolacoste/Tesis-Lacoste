package com.Tesis.admin.Models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BattleParticipationId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "appuser_id")
    private Long appuserId;

    @Column(name = "battle_id")
    private Long battleId;

    public BattleParticipationId() {
    }

    public BattleParticipationId(Long appuserId, Long battleId) {
        this.appuserId = appuserId;
        this.battleId = battleId;
    }

    public Long getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(Long appuserId) {
        this.appuserId = appuserId;
    }

    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }
}
