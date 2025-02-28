package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamEntityWithName;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TeamControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllTeams_success() throws Exception {
        teamRepository.saveAll(List.of(createTeamEntityWithName(TEAM_8_NAME), createTeamEntityWithName(TEAM_7_NAME)));
        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].name").value(hasItems(TEAM_7_NAME, TEAM_8_NAME)));
    }

    @Test
    public void testCreateTeam_success() throws Exception {
        final String requestBody = "{\"name\": \"" + TEAM_3_NAME + "\"}";
        mockMvc.perform(post("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(TEAM_3_NAME));
    }

    @Test
    public void testCreateTeam_badRequest() throws Exception {
        final String requestBody = "{\"name\": \" \"}";
        mockMvc.perform(post("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Team name cannot be empty or null"));
    }

}
