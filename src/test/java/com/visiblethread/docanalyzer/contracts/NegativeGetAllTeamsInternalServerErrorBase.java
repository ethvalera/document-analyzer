package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.ExceptionControllerAdvice;
import com.visiblethread.docanalyzer.controller.TeamController;
import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
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
public class NegativeGetAllTeamsInternalServerErrorBase {

    @InjectMocks
    private TeamController teamController;

    @InjectMocks
    private ExceptionControllerAdvice exceptionControllerAdvice;

    @Mock
    private TeamService teamService;

    @BeforeEach
    public void setup() {
        when(teamService.getAllTeams()).thenThrow(new DocAnalyzerException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        final StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders
                .standaloneSetup(teamController).setControllerAdvice(exceptionControllerAdvice);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}