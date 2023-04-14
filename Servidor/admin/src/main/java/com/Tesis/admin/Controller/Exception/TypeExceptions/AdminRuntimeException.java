package com.Tesis.admin.Controller.Exception.TypeExceptions;

public class AdminRuntimeException extends RuntimeException{

    private static final long serialVersionUID= 1L;

    private String code;

    public AdminRuntimeException(final String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
