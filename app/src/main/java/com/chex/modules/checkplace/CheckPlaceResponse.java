package com.chex.modules.checkplace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckPlaceResponse implements Serializable {
    private CheckPlaceResponseStatus responseStatus;
    private List<CheckPlaceView> checkPlaceViewList;

    public CheckPlaceResponse() {
    }

    public CheckPlaceResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(CheckPlaceResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<CheckPlaceView> getCheckPlaceViewList() {
        return checkPlaceViewList;
    }

    public void setCheckPlaceViewList(List<CheckPlaceView> checkPlaceViewList) {
        this.checkPlaceViewList = checkPlaceViewList;
    }
}
