package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.TeamController;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.service.TeamService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamWithIdAndName;

@ExtendWith(MockitoExtension.class)
public class PositiveGetAllTeamsBase {

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamService teamService;

    @BeforeEach
    public void setup() {
        Team team1 = createTeamWithIdAndName(TEAM_1_ID, TEAM_1_NAME);
        Team team2 = createTeamWithIdAndName(TEAM_2_ID, TEAM_2_NAME);
        Mockito.when(teamService.getAllTeams()).thenReturn(List.of(team1, team2));
        RestAssuredMockMvc.standaloneSetup(teamController);
    }
}