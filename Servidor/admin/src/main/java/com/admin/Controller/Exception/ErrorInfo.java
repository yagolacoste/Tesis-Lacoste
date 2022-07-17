package com.admin.Controller.Exception;

public class ErrorInfo {
    private String uriRequested;
    private int statusCode;
    private String message;






    public ErrorInfo(String message, int statusCode, String uriRequested) {
        this.message = message;
        this.statusCode = statusCode;
        this.uriRequested = uriRequested;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getUriRequested() {
        return uriRequested;
    }

    public void setUriRequested(String uriRequested) {
        this.uriRequested = uriRequested;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RestStatus [code=");
        builder.append(this.uriRequested);
        builder.append(", status=");
        builder.append(this.statusCode);
        builder.append(", ");
        if (this.message != null) {
            builder.append("statusText=");
            builder.append(this.message);
        }
        builder.append("]");
        return builder.toString();
    }
}
