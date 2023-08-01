package com.Tesis.bicycle.Dto.ApiRest.Statistics;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ClassificationDto implements Serializable {

    @JsonProperty("profileUserDto")
    private ProfileUserDto profileUserDto;

    @JsonProperty("achievementsDto")
    private AchievementsDto achievementsDto;

    public ClassificationDto() {
    }

    public ProfileUserDto getProfileUserDto() {
        return profileUserDto;
    }

    public void setProfileUserDto(ProfileUserDto profileUserDto) {
        this.profileUserDto = profileUserDto;
    }

    public AchievementsDto getAchievementsDto() {
        return achievementsDto;
    }

    public void setAchievementsDto(AchievementsDto achievementsDto) {
        this.achievementsDto = achievementsDto;
    }
}
