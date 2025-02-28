package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.persistence.entity.DocumentEntity;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import com.visiblethread.docanalyzer.persistence.repository.DocumentRepository;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import com.visiblethread.docanalyzer.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DocumentControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        TeamEntity team = createTeamEntityWithName(TEAM_4_NAME);
        teamRepository.save(team);

        UserEntity user = createUserEntityWithEmailAndTeams(EMAIL_USER_2, Set.of(team));
        userRepository.save(user);

        UserEntity savedUser = userRepository.findByEmail(EMAIL_USER_2)
                .orElseThrow(() -> new IllegalStateException("User not found in setup: " + EMAIL_USER_2));
        DocumentEntity document = createDocumentEntityWithAllParams(DOC_2_NAME, DOC_2_COUNT, savedUser);
        documentRepository.save(document);
    }

    @Test
    public void testGetAllDocuments_success() throws Exception {
        mockMvc.perform(get("/api/v1/documents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(DOC_2_NAME))
                .andExpect(jsonPath("$[0].wordCount").value(DOC_2_COUNT));
    }
}