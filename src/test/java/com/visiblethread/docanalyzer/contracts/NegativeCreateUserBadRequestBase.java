package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.ExceptionControllerAdvice;
import com.visiblethread.docanalyzer.controller.UserController;
import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NegativeCreateUserBadRequestBase {

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private ExceptionControllerAdvice exceptionControllerAdvice;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        when(userService.createUser(any(CreateUserRequest.class))).thenThrow(new DocAnalyzerException(HttpStatus.BAD_REQUEST, "The email cannot be empty or null"));
        final StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders
                .standaloneSetup(userController).setControllerAdvice(exceptionControllerAdvice);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
