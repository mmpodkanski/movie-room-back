package io.github.mmpodkanski.movieroom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiBadRequestException.class})
    public ResponseEntity<Object> handleApiBadRequestException(ApiBadRequestException e) {
        var badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(exception(e, badRequest), badRequest);
    }

    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ApiNotFoundException e) {
        var notFound = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(exception(e, notFound), notFound);
    }

    private ApiException exception(RuntimeException e, HttpStatus status) {
        return new ApiException(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
    }

}
