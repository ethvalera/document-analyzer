package com.visiblethread.docanalyzer.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends DocAnalyzerException {
    private final String fieldName;

    public EntityNotFoundException(String fieldName, String value) {
        super(HttpStatus.NOT_FOUND, "Field '" + fieldName + "' with value '" + value + "' not found");
        this.fieldName = fieldName;
    }

}