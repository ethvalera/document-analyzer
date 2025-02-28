package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.DuplicateFieldException;
import com.visiblethread.docanalyzer.exception.ValidationFailureException;
import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import com.visiblethread.docanalyzer.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MapperService mapperService;

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        validateEmail(createUserRequest.getEmail());
        List<TeamEntity> teamEntities = validateTeams(createUserRequest.getTeams());
        UserEntity userEntity = mapperService.toUserEntity(createUserRequest);
        userEntity.getTeams().addAll(teamEntities);
        UserEntity userCreated = userRepository.save(userEntity);
        return mapperService.toUser(userCreated);
    }

    private void validateEmail(String email) {
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
        if(teams == null || teams.isEmpty()) {
            throw new ValidationFailureException("The user must belong to at least one team");
        }

        List<TeamEntity> teamEntities = teamRepository.findByNameIn(teams);
        if(teamEntities.size() != teams.size()) {
            throw new ValidationFailureException("Some teams are duplicated or they do not exist, please create the team(s) first");
        }

        return teamEntities;
    }
}
