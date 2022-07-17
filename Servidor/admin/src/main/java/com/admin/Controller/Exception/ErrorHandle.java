package com.admin.Controller.Exception;


import com.admin.Controller.Exception.TypeExceptions.AdminRuntimeException;
import com.admin.Controller.Exception.TypeExceptions.BadRequestException;
import com.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.admin.Controller.Exception.TypeExceptions.UnKnownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@ControllerAdvice
public class ErrorHandle {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandle.class);


    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> badRequest(final AdminRuntimeException ex) {

        return normalizeMessage(ex, HttpStatus.BAD_REQUEST, ex.getCode());

    }


    @ExceptionHandler(value={NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(AdminRuntimeException ex) {

        return normalizeMessage(ex, HttpStatus.NOT_FOUND, ex.getCode());

    }


    @ExceptionHandler({UnKnownException.class})
    public ResponseEntity<Object> handleInternal(final AdminRuntimeException ex) {

        return normalizeMessage(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getCode());

    }



    private ResponseEntity<Object> normalizeMessage(final Throwable ex, final HttpStatus status,
                                                    String code) {

        final String message = String.format(
                "%nHTTP error: %s%nException: %s%nEx-Message: %s%nCause: %s%n", status.value(), ex
                        .getClass().getName(), ex.getMessage(), ex.getCause());


        return new ResponseEntity<>(new ErrorInfo(code, status.value(), ex.getMessage()), status);
    }
}





