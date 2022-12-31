package com.delivery.kyh.adapter.in.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalWebExceptionHandler {
    @ExceptionHandler(
        value = {
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            ValidationException.class,
        }
    )
    public ResponseEntity<Map<String, String>> handle(RuntimeException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity(Map.of("message", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Map<String, String>> handle(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity(Map.of("message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
