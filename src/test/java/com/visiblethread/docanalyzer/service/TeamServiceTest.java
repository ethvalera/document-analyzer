package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.visiblethread.docanalyzer.utils.Constants.TEAM_1_NAME;
import static com.visiblethread.docanalyzer.utils.Constants.TEAM_2_NAME;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamWithName;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.createTeamEntityWithName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    public void testGetAllTeams_success() {

        List<TeamEntity> teamEntities = List.of(createTeamEntityWithName(TEAM_1_NAME), createTeamEntityWithName(TEAM_2_NAME));

        when(teamRepository.findAll()).thenReturn(teamEntities);
        when(teamMapper.toTeam(teamEntities.get(0))).thenReturn(createTeamWithName(TEAM_1_NAME));
        when(teamMapper.toTeam(teamEntities.get(1))).thenReturn(createTeamWithName(TEAM_2_NAME));

        List<Team> retrievedTeams = teamService.getAllTeams();

        assertEquals(2, retrievedTeams.size());

    }

}
