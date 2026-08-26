package com.sarav.jobtracker.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFound(ResourceNotFoundException ex){
        Map<String,Object> errorBody=new HashMap<>();
        errorBody.put("timestamp",LocalDateTime.now());
        errorBody.put("status",HttpStatus.NOT_FOUND.value());
        errorBody.put("error","Not Found");
        errorBody.put("message",ex.getMessage());
        return new ResponseEntity<>(errorBody,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String,Object>> handleDuplicateResource(DuplicateResourceException ex){
        Map<String,Object> errorBody=new HashMap<>();
        errorBody.put("timestamp",LocalDateTime.now());
        errorBody.put("status",HttpStatus.CONFLICT.value());
        errorBody.put("error","Conflict/Duplicate");
        errorBody.put("message",ex.getMessage());
        return new ResponseEntity<>(errorBody,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGenericException(Exception ex){
        Map<String,Object> errorBody=new HashMap<>();
        errorBody.put("timestamp",LocalDateTime.now());
        errorBody.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorBody.put("error","Internal Server Error");
        errorBody.put("message",ex.getMessage());
        return new ResponseEntity<>(errorBody,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
            errors.put(error.getField(),error.getDefaultMessage())
        );
        Map<String,Object> response=new HashMap<>();
        response.put("timestamp",LocalDateTime.now());
        response.put("status",HttpStatus.BAD_REQUEST.value());
        response.put("error",errors);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidStateTransitionException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidStateTransition(InvalidStateTransitionException ex){
        Map<String,Object> errorBody=new HashMap<>();
        errorBody.put("timestamp",LocalDateTime.now());
        errorBody.put("status",HttpStatus.BAD_REQUEST.value());
        errorBody.put("error","Invalid State Transition");
        errorBody.put("message",ex.getMessage());
        return new ResponseEntity<>(errorBody,HttpStatus.BAD_REQUEST);
    }
}