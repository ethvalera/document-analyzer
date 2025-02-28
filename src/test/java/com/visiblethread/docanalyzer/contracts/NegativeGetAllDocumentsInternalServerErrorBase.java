package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.DocumentController;
import com.visiblethread.docanalyzer.controller.ExceptionControllerAdvice;
import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.service.DocumentService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;


@ExtendWith(MockitoExtension.class)
public class NegativeGetAllDocumentsInternalServerErrorBase {

    @InjectMocks
    private DocumentController documentController;

    @InjectMocks
    private ExceptionControllerAdvice exceptionControllerAdvice;

    @Mock
    private DocumentService documentService;

    @BeforeEach
    public void setup() {
        Mockito.when(documentService.getAllDocuments()).thenThrow(new DocAnalyzerException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        final StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders
                .standaloneSetup(documentController).setControllerAdvice(exceptionControllerAdvice);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}