package com.to_do_list.Metas.controller.exceptions;

import com.to_do_list.Metas.service.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHanlder {


    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<StandarError> notFoundUser(NotFoundUserException ex,HttpServletRequest request){
        var error = new StandarError(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TarefaNotFoundException.class)
    public ResponseEntity<StandarError> tarefaNotFound(TarefaNotFoundException ex,HttpServletRequest request){
        var error = new StandarError(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<StandarError> userExist(UserExistException ex,HttpServletRequest request){
        var error = new StandarError(LocalDateTime.now(),HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(IllegalArgument.class)
    public ResponseEntity<StandarError> IllegalArgumentException(IllegalArgument ex,HttpServletRequest request){
        var error = new StandarError(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(PasswordIncorreta.class)
    public ResponseEntity<StandarError> passWordIncorreta(PasswordIncorreta ex,HttpServletRequest request){
        var error = new StandarError(LocalDateTime.now(),HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
