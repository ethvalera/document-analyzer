package com.visiblethread.docanalyzer.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationFailureException extends DocAnalyzerException {

    public ValidationFailureException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}