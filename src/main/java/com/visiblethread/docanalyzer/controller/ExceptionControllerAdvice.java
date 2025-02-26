package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DocAnalyzerException.class)
    public ResponseEntity<ErrorResponse> handleDocAnalyzerException(DocAnalyzerException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
