package com.visiblethread.docanalyzer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DocAnalyzerException extends RuntimeException {
    private final HttpStatus status;

    public DocAnalyzerException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
    }

}
