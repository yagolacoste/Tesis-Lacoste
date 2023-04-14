package com.Tesis.admin.Exception;

public enum ErrorCodes {

    NOT_FOUND("Not Found"),
    ACCESS_DENIED("Access_denied"),
    FILE_STORAGE_EXCEPTION("file-storage-exception"),
    FILE_NOT_FOUND("my-file-not-found-exception");

    private String code;

    public String getCode() {

        return code;
    }

    private ErrorCodes(String code) {

        this.code = code;
    }
}
