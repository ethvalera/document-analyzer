package com.visiblethread.docanalyzer.contracts;

import com.visiblethread.docanalyzer.controller.UserController;
import com.visiblethread.docanalyzer.model.InactiveUsersResponse;
import com.visiblethread.docanalyzer.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositiveGetInactiveUsersBase {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        InactiveUsersResponse response = new InactiveUsersResponse();
        response.setInactiveUserCount(4);
        when(userService.getInactiveUsersWithinPeriod(any(LocalDate.class), any(LocalDate.class))).thenReturn(response);
        RestAssuredMockMvc.standaloneSetup(userController);
    }
}