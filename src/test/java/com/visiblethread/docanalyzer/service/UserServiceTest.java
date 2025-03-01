package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.exception.DuplicateFieldException;
import com.visiblethread.docanalyzer.exception.ValidationFailureException;
import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.InactiveUsersResponse;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import com.visiblethread.docanalyzer.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static com.visiblethread.docanalyzer.utils.TestConstants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapperService mapperService;

    @Test
    public void testCreateUser_success() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(EMAIL_USER_1);
        createUserRequest.setTeams(List.of(TEAM_1_NAME, TEAM_2_NAME));
        UserEntity userEntity = createUserEntityWithEmailAndTeams(EMAIL_USER_1, new HashSet<>());
        TeamEntity team1 = createTeamEntityWithIdAndName(TEAM_1_ID, TEAM_1_NAME);
        TeamEntity team2 = createTeamEntityWithIdAndName(TEAM_2_ID, TEAM_2_NAME);
        UserEntity savedUserEntity = createUserEntityWithEmailAndTeams(EMAIL_USER_1, new HashSet<>(List.of(team1, team2)));
        savedUserEntity.setId(USER_1_ID);
        savedUserEntity.setCreatedAt(Instant.parse(CREATED_AT));
        User user = createUserWithEmailAndTeams(EMAIL_USER_1, List.of(createTeamWithName(TEAM_1_NAME), createTeamWithName(TEAM_2_NAME)));
        user.setId(USER_1_ID);
        user.setCreatedAt(Instant.parse(CREATED_AT));

        when(mapperService.toUserEntity(createUserRequest)).thenReturn(userEntity);
        when(teamRepository.findByNameIn(List.of(TEAM_1_NAME, TEAM_2_NAME))).thenReturn(List.of(team1, team2));
        when(userRepository.save(userEntity)).thenReturn(savedUserEntity);
        when(mapperService.toUser(savedUserEntity)).thenReturn(user);

        User createdUser = userService.createUser(createUserRequest);

        assertEquals(createUserRequest.getEmail(), createdUser.getEmail());
        assertEquals(USER_1_ID, createdUser.getId());
        assertEquals(2, createdUser.getTeams().size());
        assertEquals(Instant.parse(CREATED_AT), createdUser.getCreatedAt());
    }

    @Test
    public void testCreateUser_invalidEmailFormat_badRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(INVALID_EMAIL);
        createUserRequest.setTeams(List.of(TEAM_1_NAME, TEAM_2_NAME));

        ValidationFailureException exception = assertThrows(ValidationFailureException.class, () -> userService.createUser(createUserRequest));
        assertEquals("Email: " + createUserRequest.getEmail() + " is not valid, please provide a valid email address",
                exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testCreateUser_invalidEmailNull_badRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(null);
        createUserRequest.setTeams(List.of(TEAM_1_NAME, TEAM_2_NAME));

        ValidationFailureException exception = assertThrows(ValidationFailureException.class, () -> userService.createUser(createUserRequest));
        assertEquals("Email cannot be empty or null", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testCreateUser_invalidEmailEmpty_badRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(" ");
        createUserRequest.setTeams(List.of(TEAM_1_NAME, TEAM_2_NAME));

        ValidationFailureException exception = assertThrows(ValidationFailureException.class, () -> userService.createUser(createUserRequest));
        assertEquals("Email cannot be empty or null", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testCreateUser_emailIsNotUnique_badRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(EMAIL_USER_1);
        createUserRequest.setTeams(List.of(TEAM_1_NAME, TEAM_2_NAME));

        UserEntity userEntity = createUserEntityWithEmailAndTeams(EMAIL_USER_1, new HashSet<>());
        TeamEntity team1 = createTeamEntityWithIdAndName(TEAM_1_ID, TEAM_1_NAME);
        TeamEntity team2 = createTeamEntityWithIdAndName(TEAM_2_ID, TEAM_2_NAME);

        when(mapperService.toUserEntity(createUserRequest)).thenReturn(userEntity);
        when(teamRepository.findByNameIn(List.of(TEAM_1_NAME, TEAM_2_NAME))).thenReturn(List.of(team1, team2));
        when(userRepository.save(userEntity)).thenThrow(new DuplicateFieldException("email", EMAIL_USER_1));

        DocAnalyzerException exception = assertThrows(DuplicateFieldException.class, () -> userService.createUser(createUserRequest));
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("Field 'email' with value '" + EMAIL_USER_1 + "' is already used", exception.getMessage());
    }

    @Test
    public void testCreateUser_TeamIsEmpty_badRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(EMAIL_USER_1);
        createUserRequest.setTeams(List.of());

        ValidationFailureException exception = assertThrows(ValidationFailureException.class, () -> userService.createUser(createUserRequest));
        assertEquals("The user must belong to at least one team", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testCreateUser_TeamNotFound_badRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(EMAIL_USER_1);
        createUserRequest.setTeams(List.of(TEAM_3_NAME));

        when(teamRepository.findByNameIn(List.of(TEAM_3_NAME))).thenReturn(List.of());

        ValidationFailureException exception = assertThrows(ValidationFailureException.class, () -> userService.createUser(createUserRequest));
        assertEquals("Some teams are duplicated or they do not exist, please create the team(s) first", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testGetInactiveUsers_success() {
        LocalDate startDate = LocalDate.parse("2024-04-01");
        LocalDate endDate = LocalDate.parse("2024-05-02");

        when(userRepository.countInactiveUsers(any(Instant.class), any(Instant.class))).thenReturn(3);

        InactiveUsersResponse response = userService.getInactiveUsersWithinPeriod(startDate, endDate);
        assertEquals(3, response.getInactiveUserCount());
    }

    @Test
    public void testGetInactiveUsers_startDateAfterEndDate_badRequest() {
        LocalDate startDate = LocalDate.parse("2025-04-01");
        LocalDate endDate = LocalDate.parse("2024-05-02");

        ValidationFailureException exception = assertThrows(ValidationFailureException.class,
                () -> userService.getInactiveUsersWithinPeriod(startDate, endDate));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    public void testGetInactiveUsers_nullStartDate_badRequest() {
        LocalDate endDate = LocalDate.parse("2024-05-02");

        ValidationFailureException exception = assertThrows(ValidationFailureException.class,
                () -> userService.getInactiveUsersWithinPeriod(null, endDate));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Start date and end date must not be null", exception.getMessage());
    }

    @Test
    public void testGetInactiveUsers_nullEndDate_badRequest() {
        LocalDate startDate = LocalDate.parse("2024-04-01");

        ValidationFailureException exception = assertThrows(ValidationFailureException.class,
                () -> userService.getInactiveUsersWithinPeriod(startDate, null));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Start date and end date must not be null", exception.getMessage());
    }

}
