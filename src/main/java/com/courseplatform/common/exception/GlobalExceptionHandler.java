package com.courseplatform.common.exception;

import com.courseplatform.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(
                "Not Found",
                ex.getMessage(),
                Instant.now()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Fallback (500) – required for safety
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        ErrorResponse response = new ErrorResponse(
                "Internal Server Error",
                "Something went wrong",
                Instant.now()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {

        ErrorResponse response = new ErrorResponse(
                "Already enrolled",
                ex.getMessage(),
                Instant.now()
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(
            ForbiddenException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        "Forbidden",
                        ex.getMessage(),
                        Instant.now()
                ));
    }

}
