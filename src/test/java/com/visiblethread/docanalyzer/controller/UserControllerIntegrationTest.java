package com.visiblethread.docanalyzer.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUser_success() throws Exception {
        final String requestBody = """
                {
                    "email": "elisabeth@gmail.com",
                    "teams":[
                        "Engineering"
                    ]
                
                }""";
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(EMAIL_USER_1));
    }

    @Test
    public void testCreateUser_badRequest() throws Exception {
        final String requestBody = """
                {
                    "email": "userWithTeamDoesNotExist@gmail.com",
                    "teams":[
                        "TeamThatDoesNotExist"
                    ]
                
                }""";
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Some teams are duplicated or they do not exist, please create the team(s) first"));
    }

    @Test
    public void testGetInactiveUsers_success() throws Exception {
        mockMvc.perform(get("/api/v1/users/inactive?startDate=2024-04-04&endDate=2024-04-04"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInactiveUsers_badRequest() throws Exception {
        mockMvc.perform(get("/api/v1/users/inactive?startDate=2025-04-04&endDate=2024-04-04"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Start date must be before end date"));
    }
}
