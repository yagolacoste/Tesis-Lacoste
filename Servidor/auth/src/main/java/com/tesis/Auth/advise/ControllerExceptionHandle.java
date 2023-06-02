package com.Tesis.auth.advise;

import com.Tesis.auth.security.jwt.exception.TokenRefreshException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerExceptionHandle {

    @ExceptionHandler({ BadRequestException.class })
    protected ErrorMessage badRequest(final RuntimeException ex) {

        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), ex.getCode());

    }


    @ExceptionHandler({ NotFoundException.class })
    protected ErrorMessage notFound(final RuntimeException ex) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),ex.getMessage(), ex.getCode());
    }

    @ExceptionHandler({ AccessDeniedException.class })
    protected ErrorMessage accessDenied(final RuntimeException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), ex.getCode());
    }


    @ExceptionHandler({ TokenRefreshException.class })
    protected ErrorMessage tokenRefresh(final RuntimeException ex) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(),ex.getMessage(), ex.getCode());
    }

    @ExceptionHandler({ExpiredJwtException.class})
    protected ErrorMessage expirationToken(final RuntimeException ex) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(),ex.getMessage(), ex.getCode());
    }

}
