package com.chex.modules.posts.model;

import java.io.Serializable;

public class PlaceShortView implements Comparable<PlaceShortView>, Serializable {

    private String id;
    private String name;
    private String imgUrl;

    public PlaceShortView() {
    }

    @Override
    public int compareTo(PlaceShortView placeShortView) {
        return -id.compareTo(placeShortView.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
