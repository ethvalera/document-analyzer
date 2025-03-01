package com.visiblethread.docanalyzer.contracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visiblethread.docanalyzer.controller.DocumentController;
import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.service.DocumentService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.visiblethread.docanalyzer.utils.TestConstants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositiveGetAllDocumentsBase {

    @InjectMocks
    private DocumentController documentController;

    @Mock
    private DocumentService documentService;

    @BeforeEach
    public void setup() {
        Document document1 = createDocument(DOC_1_NAME, 1500, 1L);
        document1.setId(1L);
        Document document2 = createDocument(DOC_2_NAME, 800, 1L);
        document2.setId(2L);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        when(documentService.getAllDocuments()).thenReturn(List.of(document1, document2));
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
                .standaloneSetup(documentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper)));
    }
}