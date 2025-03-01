package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.InactiveUsersResponse;
import com.visiblethread.docanalyzer.model.User;

import java.time.LocalDate;

public interface UserService {

    User createUser(CreateUserRequest createUserRequest);

    InactiveUsersResponse getInactiveUsersWithinPeriod(LocalDate startDate, LocalDate endDate);
}
