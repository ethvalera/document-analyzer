package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.DuplicateFieldException;
import com.visiblethread.docanalyzer.exception.ValidationFailureException;
import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.InactiveUsersResponse;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import com.visiblethread.docanalyzer.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MapperService mapperService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        final String email = createUserRequest.getEmail();
        logger.debug("Creating user with email: {}", email);
        validateEmail(email);
        List<TeamEntity> teamEntities = validateTeams(createUserRequest.getTeams());
        UserEntity userEntity = mapperService.toUserEntity(createUserRequest);
        userEntity.getTeams().addAll(teamEntities);
        logger.debug("Saving user entity with email: {}", email);
        UserEntity userCreated = userRepository.save(userEntity);

        logger.info("User created successfully with email: {}", email);
        return mapperService.toUser(userCreated);
    }

    @Override
    public InactiveUsersResponse getInactiveUsersWithinPeriod(LocalDate startDate, LocalDate endDate) {
        validatePeriodOfTime(startDate, endDate);
        Instant startDateInstant = startDate.atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant endDateInstant = endDate.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
        Integer count = userRepository.countInactiveUsers(startDateInstant, endDateInstant);
        return new InactiveUsersResponse(count);
    }

    private void validateEmail(String email) {
        logger.debug("Validating email: {}", email);
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationFailureException("Email cannot be empty or null");
        }

        Pattern emailPattern = Pattern.compile(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                Pattern.CASE_INSENSITIVE
        );

        if(!emailPattern.matcher(email.trim()).matches()) {
            throw new ValidationFailureException("Email: " + email + " is not valid, please provide a valid email address");
        }

        if(userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateFieldException("email", email);
        }

    }

    private List<TeamEntity> validateTeams(List<String> teams) {
        logger.debug("Validating teams for user creation");
        if(teams == null || teams.isEmpty()) {
            throw new ValidationFailureException("The user must belong to at least one team");
        }

        List<TeamEntity> teamEntities = teamRepository.findByNameIn(teams);
        if(teamEntities.size() != teams.size()) {
            throw new ValidationFailureException("Some teams are duplicated or they do not exist, please create the team(s) first");
        }

        return teamEntities;
    }

    private void validatePeriodOfTime(LocalDate startDate, LocalDate endDate) {
        logger.debug("Validating that start date is before end date");
        if(startDate.isAfter(endDate)) {
            throw new ValidationFailureException("Start date must be before end date");
        }
    }
}
