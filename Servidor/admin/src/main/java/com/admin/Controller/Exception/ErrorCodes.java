package com.admin.Controller.Exception;

public enum ErrorCodes {

    NOT_FOUND("not-found");

    private ErrorCodes(String code) {

        this.code = code;
    }


    private String code;


    public String getCode() {

        return code;
    }
}
