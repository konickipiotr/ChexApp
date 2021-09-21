package com.chex.modules.checkplace;

import com.chex.module.posts.PostVisibility;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

public class AchievedPlaceDTO implements Serializable {

    private Map<String, Integer> achievedPlaces;
    private LocalDateTime timestamp;
    private String description;
    private List<String> sfiles = new ArrayList<>();
    private PostVisibility postvisibility = PostVisibility.PUBLIC;

    public void addSFile(String sFile){
        sfiles.add(sFile);
    }

    public AchievedPlaceDTO() {
    }

    public Map<String, Integer> getAchievedPlaces() {
        return achievedPlaces;
    }

    public void setAchievedPlaces(Map<String, Integer> achievedPlaces) {
        this.achievedPlaces = achievedPlaces;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSfiles() {
        return sfiles;
    }

    public void setSfiles(List<String> sfiles) {
        this.sfiles = sfiles;
    }

    public PostVisibility getPostvisibility() {
        return postvisibility;
    }

    public void setPostvisibility(PostVisibility postvisibility) {
        this.postvisibility = postvisibility;
    }
}
