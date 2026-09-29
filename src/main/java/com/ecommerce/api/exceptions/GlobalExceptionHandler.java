package com.ecommerce.api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest req){
        var body = new ErrorResponseDto(
            ex.getStatusCode().value(),
            ex.getReason().toString(),
            ex.getReason(),
            req.getRequestURI(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
        MethodArgumentNotValidException ex, HttpServletRequest req )
    {
        String message = ex.getBindingResult().getAllErrors().stream()
            .map(err -> {
                if (err instanceof org.springframework.validation.FieldError fieldErr) {
                    return fieldErr.getField() + ": " + err.getDefaultMessage();
                }
                return err.getDefaultMessage();
            })
            .collect(Collectors.joining("; "));

        var body = new ErrorResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            message,
            req.getRequestURI(),
            LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleOtherException(Exception ex, HttpServletRequest req){
        ex.printStackTrace();

        var body = new ErrorResponseDto(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "Something went wrong, please try again later.",
            req.getRequestURI(),
            LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
