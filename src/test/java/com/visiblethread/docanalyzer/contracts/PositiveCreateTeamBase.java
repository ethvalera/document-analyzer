package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.TeamController;
import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.service.TeamService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.visiblethread.docanalyzer.utils.TestConstants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamWithIdAndName;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositiveCreateTeamBase {

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamService teamService;

    @BeforeEach
    public void setup() {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest(TEAM_1_NAME);
        Team team = createTeamWithIdAndName(TEAM_1_ID, TEAM_1_NAME);
        when(teamService.createTeam(createTeamRequest)).thenReturn(team);
        RestAssuredMockMvc.standaloneSetup(teamController);
    }
}