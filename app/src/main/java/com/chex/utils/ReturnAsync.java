package com.chex.utils;

public class ReturnAsync<T> {

    private RetStatus retStatus;
    private T val;

    public ReturnAsync(RetStatus retStatus) {
        this.retStatus = retStatus;
    }

    public ReturnAsync() {
        this.retStatus = RetStatus.OK;
    }

    public ReturnAsync(RetStatus retStatus, T val) {
        this.retStatus = retStatus;
        this.val = val;
    }

    public RetStatus getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(RetStatus retStatus) {
        this.retStatus = retStatus;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }
}
