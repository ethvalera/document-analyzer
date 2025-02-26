package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import com.visiblethread.docanalyzer.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.visiblethread.docanalyzer.utils.Constants.TEAM_1_NAME;
import static com.visiblethread.docanalyzer.utils.Constants.TEAM_2_NAME;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamEntityWithName;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class TeamControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TeamRepository teamRepository;

    @MockitoSpyBean
    private TeamService teamService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        teamRepository.deleteAllInBatch();
        teamRepository.saveAll(List.of(createTeamEntityWithName(TEAM_1_NAME), createTeamEntityWithName(TEAM_2_NAME)));
    }

    @Test
    public void testGetAllTeams_success() throws Exception {
        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].name").value(containsInAnyOrder(TEAM_1_NAME, TEAM_2_NAME)));
    }

    @Test
    public void testGetAllTeams_internalServerError() throws Exception {
        doThrow(new DocAnalyzerException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"))
                .when(teamService).getAllTeams();

        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }

}
