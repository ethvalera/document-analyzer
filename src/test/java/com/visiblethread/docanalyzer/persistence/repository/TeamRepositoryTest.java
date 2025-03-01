package com.visiblethread.docanalyzer.persistence.repository;

import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamEntityWithName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void testSaveTeamAndFindAll() {
        assertEquals(4, teamRepository.findAll().size());

        TeamEntity teamEntity = createTeamEntityWithName(TEAM_3_NAME);
        TeamEntity teamEntitySaved = teamRepository.save(teamEntity);
        Optional<TeamEntity> teamEntityRetrieved = teamRepository.findById(teamEntitySaved.getId());

        assertTrue(teamEntityRetrieved.isPresent());
        assertEquals(5, teamRepository.findAll().size());
    }

}
