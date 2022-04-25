/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.m3.bullsncows.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Jeonghoon
 */
@ControllerAdvice
public class GameControllerExceptionHanlder extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameFinishedException.class)
    public ResponseEntity<Object> handleGameFinishedException(
            GameFinishedException ex, WebRequest request) {

        Error err = new Error();
        err.setMessage(ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({GameNotFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleNotFoundException(
            RuntimeException ex, WebRequest request) {

        Error err = new Error();
        err.setMessage(ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestParametersException.class)
    public ResponseEntity<Object> handleInvalidRequestParametersException(
            InvalidRequestParametersException ex, WebRequest request) {

        Error err = new Error();
        err.setMessage(ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {
        Error err = new Error();
        err.setMessage("Database error");

        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
