package com.tesis.Auth.advise;

public class RuntimeException extends java.lang.RuntimeException {
    private static final long serialVersionUID = 1L;

    private static String defaultCode = "not-specific-error";

    private String code;


    public RuntimeException(final String message,String code) {
        super(message);
        this.code = code;
    }

    public static String getDefaultCode() {
        return defaultCode;
    }

    public static void setDefaultCode(String defaultCode) {
        RuntimeException.defaultCode = defaultCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
