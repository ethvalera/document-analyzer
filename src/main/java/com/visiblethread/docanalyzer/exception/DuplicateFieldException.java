package com.visiblethread.docanalyzer.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateFieldException extends DocAnalyzerException {
    private final String fieldName;

    public DuplicateFieldException(String fieldName, String value) {
        super(HttpStatus.CONFLICT, "Field '" + fieldName + "' with value '" + value + "' is already used");
        this.fieldName = fieldName;
    }

}