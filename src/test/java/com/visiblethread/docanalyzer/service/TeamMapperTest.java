package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.visiblethread.docanalyzer.utils.Constants.TEAM_1_NAME;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamEntityWithName;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TeamMapperTest {

    @Autowired
    private TeamMapper teamMapper;

    @Test
    public void testToTeam_success() {
        TeamEntity teamEntity = createTeamEntityWithName(TEAM_1_NAME);
        Team team = teamMapper.toTeam(teamEntity);
        assertEquals(teamEntity.getName(), team.getName());
        assertEquals(teamEntity.getId(), team.getId());
    }

}
