package com.visiblethread.docanalyzer.contracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visiblethread.docanalyzer.controller.UserController;
import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamWithIdAndName;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createUserWithEmailAndTeams;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositiveCreateUserBase {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        User user = createUserWithEmailAndTeams(EMAIL_USER_1,
                List.of(createTeamWithIdAndName(TEAM_1_ID, TEAM_1_NAME), createTeamWithIdAndName(TEAM_2_ID, TEAM_2_NAME)));
        user.setId(USER_1_ID);
        user.setCreatedAt(Instant.parse(CREATED_AT));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(user);
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
                .standaloneSetup(userController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper)));
    }
}
