package com.shop.coreutils.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomExceptionResponse> handleNullPointerException(NullPointerException exception, WebRequest webRequest){
        CustomExceptionResponse customExceptionResponse = CustomExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .errorMessage(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode("NULL_POINTER_EXCEPTION")
                .build();

        return new ResponseEntity<CustomExceptionResponse>(customExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<CustomExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){

        CustomExceptionResponse customExceptionResponse = CustomExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .errorMessage(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode("NOT_FOUND")
                .build();

        return new ResponseEntity<CustomExceptionResponse>(customExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                                HttpHeaders headers,
                                                                                HttpStatusCode status,
                                                                                WebRequest request)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        });

        return new ResponseEntity<Object>(new CustomValidationExceptionResponse (LocalDateTime.now(),
                "Invalid input parameters",
                request.getDescription(false),
                "BAD_REQUEST",
                errors),
                HttpStatus.BAD_REQUEST);
    }


}