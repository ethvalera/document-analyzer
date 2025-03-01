package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.api.UsersApi;
import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.InactiveUsersResponse;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class UserController implements UsersApi {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        logger.debug("Received request to create user: {}", createUserRequest.getEmail());
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<InactiveUsersResponse> getInactiveUsers(@RequestParam("startDate") LocalDate startDate,
                                                                  @RequestParam("endDate") LocalDate endDate) {
        logger.debug("Received request to retrieve count of inactive users");
        return new ResponseEntity<>(userService.getInactiveUsersWithinPeriod(startDate, endDate), HttpStatus.OK);
    }
}
