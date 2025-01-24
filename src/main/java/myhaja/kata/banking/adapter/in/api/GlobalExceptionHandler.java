package myhaja.kata.banking.adapter.in.api;

import jakarta.persistence.EntityNotFoundException;
import myhaja.kata.banking.domain.exception.DepositException;
import myhaja.kata.banking.domain.exception.WithdrawException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DepositException.class)
    public ResponseEntity<Map<String, String>> handleDepositException(DepositException depositException) {
        var response = new HashMap<String, String>();
        response.put("message", depositException.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(WithdrawException.class)
    public ResponseEntity<Map<String, String>> handleWithdrawException(WithdrawException withdrawException) {
        var response = new HashMap<String, String>();
        response.put("message", withdrawException.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        var response = new HashMap<String, String>();
        response.put("message", ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        var response = new HashMap<String, String>();
        response.put("message", "Global Error: " + ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("General Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
