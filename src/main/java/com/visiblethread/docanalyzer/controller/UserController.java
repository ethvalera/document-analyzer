package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.api.UsersApi;
import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController implements UsersApi {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<User> createUser(CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.CREATED);
    }
}
