package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.ExceptionControllerAdvice;
import com.visiblethread.docanalyzer.controller.TeamController;
import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.service.TeamService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class NegativeCreateTeamConflictBase {

    @InjectMocks
    private TeamController teamController;

    @InjectMocks
    private ExceptionControllerAdvice exceptionControllerAdvice;

    @Mock
    private TeamService teamService;

    @BeforeEach
    public void setup() {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest("RepeatedTeam");
        when(teamService.createTeam(createTeamRequest)).thenThrow(new DocAnalyzerException(HttpStatus.CONFLICT, "Field 'team name' with value 'RepeatedTeam' is already used"));
        final StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders
                .standaloneSetup(teamController).setControllerAdvice(exceptionControllerAdvice);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}