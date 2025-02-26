package com.visiblethread.docanalyzer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DocAnalyzerException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public DocAnalyzerException(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
