package com.visiblethread.docanalyzer.persistence.repository;

import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAllInBatch();
        teamRepository.deleteAllInBatch();
        teamRepository.saveAll(List.of(createTeamEntityWithName(TEAM_1_NAME), createTeamEntityWithName(TEAM_2_NAME)));
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        TeamEntity team = teamRepository.findByNameIn(List.of(TEAM_1_NAME)).get(0);
        Set<TeamEntity> teams = new HashSet<>();
        teams.add(team);

        UserEntity userEntity = createUserEntityWithEmailAndTeams(EMAIL_USER_1, teams);
        UserEntity userEntitySaved = userRepository.save(userEntity);

        assertEquals(userEntity.getEmail(), userEntitySaved.getEmail());
        assertEquals(1, userEntity.getTeams().size());
    }
}
