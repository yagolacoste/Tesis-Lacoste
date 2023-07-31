package com.Tesis.admin.Dto.Statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

  @JsonProperty("timeMinDate")
  private Date timeMinDate;

  @JsonProperty("timeMin")
  private LocalTime timeMin;



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




  public Date getTimeMinDate() {
    return timeMinDate;
  }

  public void setTimeMinDate(Date timeMinDate) {
    this.timeMinDate = timeMinDate;
  }

  public LocalTime getTimeMin() {
    return timeMin;
  }

  public void setTimeMin(LocalTime timeMin) {
    this.timeMin = timeMin;
  }
}
