package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.User;

public interface UserService {

    User createUser(CreateUserRequest createUserRequest);
}
