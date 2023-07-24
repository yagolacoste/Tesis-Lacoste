package com.Tesis.admin.Dto.Battle;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ScoreDto implements Serializable {

    @JsonProperty("namePlayerOneComplete")
    private String namePlayerOneComplete;

    @JsonProperty("namePlayerTwoComplete")
    private String namePlayerTwoComplete;

    @JsonProperty("playerOneScore")
    private Long playerOneScore;

    @JsonProperty("playerTwoScore")
    private Long playerTwoScore;

    public ScoreDto() {
    }

    public ScoreDto(String namePlayerOneComplete, Long playerOneScore,String namePlayerTwoComplete, Long playerTwoScore) {
        this.namePlayerOneComplete = namePlayerOneComplete;
        this.namePlayerTwoComplete = namePlayerTwoComplete;
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    public String getNamePlayerOneComplete() {
        return namePlayerOneComplete;
    }

    public void setNamePlayerOneComplete(String namePlayerOneComplete) {
        this.namePlayerOneComplete = namePlayerOneComplete;
    }

    public String getNamePlayerTwoComplete() {
        return namePlayerTwoComplete;
    }

    public void setNamePlayerTwoComplete(String namePlayerTwoComplete) {
        this.namePlayerTwoComplete = namePlayerTwoComplete;
    }

    public Long getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(Long playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public Long getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(Long playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }
}
