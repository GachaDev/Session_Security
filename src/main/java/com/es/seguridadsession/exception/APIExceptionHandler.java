package com.es.seguridadsession.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class, NumberFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageForClient handleBadRequest(HttpServletRequest request, Exception exception) {
        return new ErrorMessageForClient(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageForClient handleNotFound(HttpServletRequest request, Exception exception) {
        return new ErrorMessageForClient(exception.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorMessageForClient handleInternalServerError(HttpServletRequest request, Exception exception) {
        return new ErrorMessageForClient(exception.getMessage(), request.getRequestURI());
    }
}
