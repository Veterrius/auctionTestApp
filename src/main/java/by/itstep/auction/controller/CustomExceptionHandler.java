package by.itstep.auction.controller;

import by.itstep.auction.security.exception.JwtAuthenticationException;
import by.itstep.auction.service.exceptions.InvalidItemException;
import by.itstep.auction.service.exceptions.LotAlreadyExistsException;
import by.itstep.auction.service.exceptions.NotEnoughMoneyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidItemException.class)
    public ResponseEntity<?> handleInvalidItemException(InvalidItemException ex) {
        Map<Object, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<?> handleNotEnoughMoneyException(NotEnoughMoneyException ex) {
        Map<Object, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(LotAlreadyExistsException.class)
    public ResponseEntity<?> handleInvalidItemException(LotAlreadyExistsException ex) {
        Map<Object, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<?> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        Map<Object, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
