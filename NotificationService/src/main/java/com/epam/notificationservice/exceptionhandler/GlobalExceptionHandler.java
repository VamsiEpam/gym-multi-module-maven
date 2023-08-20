package com.epam.notificationservice.exceptionhandler;

import com.epam.notificationservice.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String EXCEPTION_RAISED_MESSAGE = "Exception raised: {}";

    @ExceptionHandler(value = MailException.class)
    public ResponseEntity<ExceptionResponse> handleMailException(MailException mailException,WebRequest request) {
        log.debug(EXCEPTION_RAISED_MESSAGE, Arrays.toString(mailException.getStackTrace()));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),mailException.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.name(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFields(MethodArgumentNotValidException exception, WebRequest request) {
        log.debug(EXCEPTION_RAISED_MESSAGE,Arrays.toString(exception.getStackTrace()));
        StringBuilder message=new StringBuilder();
        exception.getAllErrors().forEach(error ->
                message.append(error.getDefaultMessage()).append("\n")
        );
        ExceptionResponse entityResponse = new ExceptionResponse(new Date().toString(),message.toString(), HttpStatus.BAD_REQUEST.name(), request.getDescription(false));
        return new ResponseEntity<>(entityResponse,HttpStatus.BAD_REQUEST);
    }
}
