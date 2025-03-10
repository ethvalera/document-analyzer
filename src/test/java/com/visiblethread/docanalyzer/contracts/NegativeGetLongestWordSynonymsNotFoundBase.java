package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.DocumentController;
import com.visiblethread.docanalyzer.controller.ExceptionControllerAdvice;
import com.visiblethread.docanalyzer.exception.EntityNotFoundException;
import com.visiblethread.docanalyzer.service.DocumentService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class NegativeGetLongestWordSynonymsNotFoundBase {

    @InjectMocks
    private DocumentController documentController;

    @InjectMocks
    private ExceptionControllerAdvice exceptionControllerAdvice;

    @Mock
    private DocumentService documentService;

    @BeforeEach
    public void setup() {
        when(documentService.getLongestWordSynonyms(5300L))
                .thenThrow(new EntityNotFoundException("Document ID", "5300"));
        final StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders
                .standaloneSetup(documentController).setControllerAdvice(exceptionControllerAdvice);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}