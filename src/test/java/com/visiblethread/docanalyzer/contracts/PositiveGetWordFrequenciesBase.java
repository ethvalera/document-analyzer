package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.DocumentController;
import com.visiblethread.docanalyzer.model.WordFrequency;
import com.visiblethread.docanalyzer.service.DocumentService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositiveGetWordFrequenciesBase {

    @InjectMocks
    private DocumentController documentController;

    @Mock
    private DocumentService documentService;

    @BeforeEach
    public void setup() {
        List<WordFrequency> wordFrequencies = Arrays.asList(
                new WordFrequency("contract", 2L),
                new WordFrequency("and", 2L),
                new WordFrequency("the", 2L),
                new WordFrequency("terms", 1L),
                new WordFrequency("conditions", 1L),
                new WordFrequency("signed", 1L),
                new WordFrequency("with", 1L),
                new WordFrequency("pen", 1L),
                new WordFrequency("sent", 1L),
                new WordFrequency("letter", 1L)
        );

        when(documentService.getWordFrequenciesForDocument(100L)).thenReturn(wordFrequencies);

        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
                .standaloneSetup(documentController));
    }
}