package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.DocumentController;
import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.LongestWordSynonyms;
import com.visiblethread.docanalyzer.service.DocumentService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.visiblethread.docanalyzer.utils.TestConstants.DOC_1_NAME;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createDocument;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositiveGetLongestWordSynonymsBase {

    @InjectMocks
    private DocumentController documentController;

    @Mock
    private DocumentService documentService;

    @BeforeEach
    public void setup() {
        Document document1 = createDocument(DOC_1_NAME, 1500, 1L);
        document1.setId(1L);

        LongestWordSynonyms longestWordSynonyms = new LongestWordSynonyms(
                "conditions",
                List.of("terms", "stipulations", "requirements", "provisions", "circumstances")
        );

        when(documentService.getLongestWordSynonyms(100L)).thenReturn(longestWordSynonyms);

        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
                .standaloneSetup(documentController));
    }
}