package com.visiblethread.docanalyzer.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.visiblethread.docanalyzer.utils.TestConstants.*;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class DocumentControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllDocuments_success() throws Exception {
        mockMvc.perform(get("/api/v1/documents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].name").value(hasItems(DOC_1_TXT, DOC_2_TXT, DOC_3_TXT, DOC_4_TXT
                ,DOC_5_TXT, DOC_6_TXT, DOC_7_TXT, DOC_8_TXT)))
                .andExpect(jsonPath("$[*].wordCount").value(hasItems(DOC_1_TXT_COUNT, DOC_2_TXT_COUNT,
                        DOC_3_TXT_COUNT, DOC_4_TXT_COUNT, DOC_5_TXT_COUNT, DOC_6_TXT_COUNT, DOC_7_TXT_COUNT, DOC_8_TXT_COUNT)));
    }

    @Test
    public void testGetWordsFrequencies_invalidDocumentId_notFoundError() throws Exception {
        mockMvc.perform(get("/api/v1/documents/5500/word-frequencies"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetWordsFrequencies_success() throws Exception {
        mockMvc.perform(get("/api/v1/documents/100/word-frequencies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].word").value("contract"))
                .andExpect(jsonPath("$[0].count").value("2"));
    }
}