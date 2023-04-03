package com.Tesis.admin.Controller.Exception.TypeExceptions;

public class NotFoundException extends AdminRuntimeException{

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message, String code) {
        super(message, code);
    }
}
