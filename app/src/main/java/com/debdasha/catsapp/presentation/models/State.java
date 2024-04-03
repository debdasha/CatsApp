package com.debdasha.catsapp.presentation.models;

public class State {
    private STATUS status;

    public State success() {
        this.status = STATUS.SUCCESS;
        return this;
    }

    public State error() {
        this.status = STATUS.ERROR;
        return this;
    }

    public State loading() {
        this.status = STATUS.LOADING;
        return this;
    }

    public boolean isError() {
        return status == STATUS.ERROR;
    }

    public boolean isSuccess() {
        return status == STATUS.SUCCESS;
    }

    public boolean isLoading() {
        return status == STATUS.LOADING;
    }

    public enum STATUS {SUCCESS, LOADING, ERROR}
}
