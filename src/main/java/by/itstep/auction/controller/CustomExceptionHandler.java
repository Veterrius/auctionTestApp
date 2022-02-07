package by.itstep.auction.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger l = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<Object, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        l.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

//    @ExceptionHandler(InvalidItemException.class)
//    public ResponseEntity<?> handleInvalidItemException(InvalidItemException ex) {
//        Map<Object, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        l.error(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
//    }
//
//    @ExceptionHandler(MoneyException.class)
//    public ResponseEntity<?> handleNotEnoughMoneyException(MoneyException ex) {
//        Map<Object, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        l.error(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
//    }
//
//    @ExceptionHandler(LotAlreadyExistsException.class)
//    public ResponseEntity<?> handleInvalidItemException(LotAlreadyExistsException ex) {
//        Map<Object, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        l.error(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
//    }
//
//    @ExceptionHandler(JwtAuthenticationException.class)
//    public ResponseEntity<?> handleJwtAuthenticationException(JwtAuthenticationException ex) {
//        Map<Object, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        l.error(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
//    }
//
//    @ExceptionHandler(LobbyException.class)
//    public ResponseEntity<?> handleLobbyException(LobbyException ex) {
//        Map<Object, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        l.error(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
//    }
}
