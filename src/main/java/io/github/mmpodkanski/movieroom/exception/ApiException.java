package io.github.mmpodkanski.movieroom.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    ApiException(
            String message,
            HttpStatus httpStatus,
            ZonedDateTime timestamp
    ) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
