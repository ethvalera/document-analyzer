package com.visiblethread.docanalyzer.persistence.repository;

import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamEntityWithName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    @Transactional
    public void setup() {
        teamRepository.deleteAllInBatch();
        teamRepository.saveAll(List.of(createTeamEntityWithName(TEAM_1_NAME), createTeamEntityWithName(TEAM_2_NAME)));
    }

    @Test
    public void testGetAllTeams_success() {
        List<TeamEntity> teams = teamRepository.findAll();
        List<String> names = teams.stream().map(TeamEntity::getName).toList();

        assertEquals(2, teams.size());
        assertTrue(names.contains(TEAM_1_NAME));
        assertTrue(names.contains(TEAM_2_NAME));
    }

}
