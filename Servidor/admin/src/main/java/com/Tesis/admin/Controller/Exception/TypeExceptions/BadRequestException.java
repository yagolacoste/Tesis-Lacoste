package com.Tesis.admin.Controller.Exception.TypeExceptions;

public class BadRequestException extends AdminRuntimeException{

    private static final long serialVersionUID = 1L;
    public BadRequestException(String message, String code) {
        super(message, code);
    }
}
