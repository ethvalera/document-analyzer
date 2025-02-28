package com.visiblethread.docanalyzer.persistence.repository;

import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
        teamRepository.saveAll(List.of(createTeamEntityWithName(TEAM_1_NAME), createTeamEntityWithName(TEAM_2_NAME)));
    }

    @Test
    @Transactional
    public void testSaveUserAndFindByEmail() {
        TeamEntity team = teamRepository.findByNameIn(List.of(TEAM_1_NAME)).get(0);
        Set<TeamEntity> teams = new HashSet<>();
        teams.add(team);

        UserEntity userEntity = createUserEntityWithEmailAndTeams(EMAIL_USER_1, teams);
        userRepository.save(userEntity);

        Optional<UserEntity> userRetrieved = userRepository.findByEmail(EMAIL_USER_1);
        assertTrue(userRetrieved.isPresent());
        assertEquals(userEntity.getEmail(), userRetrieved.get().getEmail());
        assertEquals(1, userRetrieved.get().getTeams().size());
    }
}
