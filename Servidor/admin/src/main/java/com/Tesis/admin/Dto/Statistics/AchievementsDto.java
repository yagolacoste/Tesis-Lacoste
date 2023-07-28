package com.Tesis.admin.Dto.Statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


public class AchievementsDto implements Serializable {

  @JsonProperty("battleWinner")
  private int battleWinner;

  @JsonProperty("speedMaxDate")
  private Date speedMaxDate;

  @JsonProperty("speedMax")
  private float speedMax;

  @JsonProperty("distanceMaxDate")
  private Date distanceMaxDate;

  @JsonProperty("distanceMax")
  private float distanceMax;

  @JsonProperty("timeMaxDate")
  private Date timeMaxDate;

  @JsonProperty("timeMax")
  private LocalDateTime timeMax;



  public AchievementsDto() {

  }



  public int getBattleWinner() {

    return battleWinner;
  }



  public void setBattleWinner(int battleWinner) {

    this.battleWinner = battleWinner;
  }



  public Date getSpeedMaxDate() {

    return speedMaxDate;
  }



  public void setSpeedMaxDate(Date speedMaxDate) {

    this.speedMaxDate = speedMaxDate;
  }



  public float getSpeedMax() {

    return speedMax;
  }



  public void setSpeedMax(float speedMax) {

    this.speedMax = speedMax;
  }



  public Date getDistanceMaxDate() {

    return distanceMaxDate;
  }



  public void setDistanceMaxDate(Date distanceMaxDate) {

    this.distanceMaxDate = distanceMaxDate;
  }



  public float getDistanceMax() {

    return distanceMax;
  }



  public void setDistanceMax(float distanceMax) {

    this.distanceMax = distanceMax;
  }



  public Date getTimeMaxDate() {

    return timeMaxDate;
  }



  public void setTimeMaxDate(Date timeMaxDate) {

    this.timeMaxDate = timeMaxDate;
  }



  public LocalDateTime getTimeMax() {

    return timeMax;
  }



  public void setTimeMax(LocalDateTime timeMax) {

    this.timeMax = timeMax;
  }
}
