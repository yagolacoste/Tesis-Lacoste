package com.Tesis.bicycle.Dto;


import java.util.Date;

public class InfomationDto {

    private Double speed;

    private Long timeSpeed;

    private Double kilometres;

    private Date timesSession;



    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Long getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(Long timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    public Date getTimesSession() {
        return timesSession;
    }

    public void setTimesSession(Date timesSession) {
        this.timesSession = timesSession;
    }
}
