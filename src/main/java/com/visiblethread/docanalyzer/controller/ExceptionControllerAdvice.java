package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DocAnalyzerException.class)
    public ResponseEntity<ErrorResponse> handleDocAnalyzerException(DocAnalyzerException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatus().value(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
