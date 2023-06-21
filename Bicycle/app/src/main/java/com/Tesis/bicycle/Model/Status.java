package com.Tesis.bicycle.Model;

public enum Status {
    PROGRESS("Progress"),
    NOT_STARTED("Not Started"),
    FINISHED("Finished");


    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}