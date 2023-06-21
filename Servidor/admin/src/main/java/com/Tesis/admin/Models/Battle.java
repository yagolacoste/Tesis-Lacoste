package com.Tesis.admin.Models;



import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="battle")
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Battle", nullable = false)
    private Long idBattle;

    @Column(name="complete_date")
    private Date completeDate;

    @Column(name="cant_participants")
    private Integer cantParticipant;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;


    public Battle() {
    }


    public Long getIdBattle() {
        return idBattle;
    }

    public void setIdBattle(Long idBattle) {
        this.idBattle = idBattle;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Integer getCantParticipant() {
        return cantParticipant;
    }

    public void setCantParticipant(Integer cantParticipant) {
        this.cantParticipant = cantParticipant;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
