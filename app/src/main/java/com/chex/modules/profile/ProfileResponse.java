package com.chex.modules.profile;

import com.chex.modules.Achievements.AchievementView;
import com.chex.modules.place.PlaceView;

import java.util.List;

public class ProfileResponse {

    private int placeNum;
    private int achNum;
    private int friendsNum;
    private int allPlaces;
    private int allAchievements;
    private List<PlaceView> places;
    private List<PlaceView> lastThree;
    private List<AchievementView> achievementViews;
    private List<AchievementView> achInProgress;

    public ProfileResponse() {
    }

    public int getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(int placeNum) {
        this.placeNum = placeNum;
    }

    public int getAchNum() {
        return achNum;
    }

    public void setAchNum(int achNum) {
        this.achNum = achNum;
    }

    public int getFriendsNum() {
        return friendsNum;
    }

    public void setFriendsNum(int friendsNum) {
        this.friendsNum = friendsNum;
    }

    public int getAllPlaces() {
        return allPlaces;
    }

    public void setAllPlaces(int allPlaces) {
        this.allPlaces = allPlaces;
    }

    public int getAllAchievements() {
        return allAchievements;
    }

    public void setAllAchievements(int allAchievements) {
        this.allAchievements = allAchievements;
    }

    public List<PlaceView> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceView> places) {
        this.places = places;
    }

    public List<PlaceView> getLastThree() {
        return lastThree;
    }

    public void setLastThree(List<PlaceView> lastThree) {
        this.lastThree = lastThree;
    }

    public List<AchievementView> getAchievementViews() {
        return achievementViews;
    }

    public void setAchievementViews(List<AchievementView> achievementViews) {
        this.achievementViews = achievementViews;
    }

    public List<AchievementView> getAchInProgress() {
        return achInProgress;
    }

    public void setAchInProgress(List<AchievementView> achInProgress) {
        this.achInProgress = achInProgress;
    }
}
