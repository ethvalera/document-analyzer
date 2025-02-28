package com.visiblethread.docanalyzer.persistence.repository;

import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamEntityWithName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
        teamRepository.saveAll(List.of(createTeamEntityWithName(TEAM_1_NAME), createTeamEntityWithName(TEAM_2_NAME)));
    }

    @Test
    public void testFindAllTeams() {
        List<TeamEntity> teams = teamRepository.findAll();
        List<String> names = teams.stream().map(TeamEntity::getName).toList();

        assertEquals(2, teams.size());
        assertTrue(names.contains(TEAM_1_NAME));
        assertTrue(names.contains(TEAM_2_NAME));
    }

    @Test
    public void testSaveTeam() {
        TeamEntity teamEntity = createTeamEntityWithName(TEAM_3_NAME);
        TeamEntity teamEntitySaved = teamRepository.save(teamEntity);
        Optional<TeamEntity> teamEntityRetrieved = teamRepository.findById(teamEntitySaved.getId());

        assertTrue(teamEntityRetrieved.isPresent());
        assertEquals(teamEntity.getName(), teamEntityRetrieved.get().getName());
    }

}
