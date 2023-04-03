package com.Tesis.admin.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandle {

    @ExceptionHandler({ NotFoundException.class })
    protected ErrorMessage notFound(final RuntimeException ex) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),ex.getMessage(), ex.getCode());
    }

    @ExceptionHandler({ AccessDeniedException.class })
    protected ErrorMessage accessDenied(final RuntimeException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), ex.getCode());
    }

    @ExceptionHandler({ FileNotFoundException.class })
    protected ErrorMessage fileNotFoundException(final RuntimeException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(),ErrorCodes.FILE_NOT_FOUND.getCode(), ex.getCode());
    }

    @ExceptionHandler({ FileStorageException.class })
    protected ErrorMessage fileStorageException(final RuntimeException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(),ErrorCodes.FILE_STORAGE_EXCEPTION.getCode(), ex.getCode());
    }

}
