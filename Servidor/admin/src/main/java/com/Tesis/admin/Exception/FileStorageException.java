package com.Tesis.admin.Exception;

public class FileStorageException extends RuntimeException {


    public FileStorageException(String message, String code) {
        super(message, code);
    }
}
