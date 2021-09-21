package com.chex.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListWrapper<T> implements Serializable {

    private List<T> list;

    public ListWrapper() {
    }

    public boolean isEmpty(){
        if(list == null || list.isEmpty())
            return true;
        return false;
    }

    public ListWrapper(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
