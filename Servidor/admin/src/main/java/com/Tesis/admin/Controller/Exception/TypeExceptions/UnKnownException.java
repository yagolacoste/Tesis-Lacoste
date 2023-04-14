package com.Tesis.admin.Controller.Exception.TypeExceptions;

public class UnKnownException extends AdminRuntimeException{

    private static final long serialVersionUID = 1L;

    public UnKnownException(String message, String code) {
        super(message, code);
    }


}
