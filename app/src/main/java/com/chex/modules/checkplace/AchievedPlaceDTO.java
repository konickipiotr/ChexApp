package com.chex.modules.checkplace;

import com.chex.modules.posts.PostVisibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AchievedPlaceDTO implements Serializable {

    private Map<String, Integer> achievedPlaces;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
