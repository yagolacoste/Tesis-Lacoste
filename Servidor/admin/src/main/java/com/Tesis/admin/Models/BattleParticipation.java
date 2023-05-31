package com.Tesis.admin.Models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "battle_participation")
public class BattleParticipation {

    @EmbeddedId
    private BattleParticipationId id;

    @ManyToOne
    @MapsId(value = "appuserId")
    @JoinColumn(name = "appuser_id")
    private User user;

    @ManyToOne
    @MapsId(value = "battleId")
    @JoinColumn(name = "battle_id")
    private Battle battle;

    @ManyToOne
    @JoinColumn(name = "statistics_id")
    private Statistics statistics;

    public BattleParticipationId getId() {
        return id;
    }

    public void setId(BattleParticipationId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}
