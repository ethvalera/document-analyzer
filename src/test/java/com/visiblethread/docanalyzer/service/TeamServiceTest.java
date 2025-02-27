package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private MapperService mapperService;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    public void testGetAllTeams_success() {

        List<TeamEntity> teamEntities = List.of(createTeamEntityWithName(TEAM_1_NAME), createTeamEntityWithName(TEAM_2_NAME));

        when(teamRepository.findAll()).thenReturn(teamEntities);
        when(mapperService.toTeam(teamEntities.get(0))).thenReturn(createTeamWithName(TEAM_1_NAME));
        when(mapperService.toTeam(teamEntities.get(1))).thenReturn(createTeamWithName(TEAM_2_NAME));

        List<Team> retrievedTeams = teamService.getAllTeams();

        assertEquals(2, retrievedTeams.size());

    }

    @Test
    public void testCreateTeam_success() {

        CreateTeamRequest createTeamRequest = new CreateTeamRequest(TEAM_3_NAME);
        TeamEntity teamEntity = createTeamEntityWithName(TEAM_3_NAME);
        TeamEntity teamEntitySaved = createTeamEntityWithIdAndName(TEAM_3_ID, TEAM_3_NAME);
        Team team = createTeamWithIdAndName(TEAM_3_ID, TEAM_3_NAME);

        when(teamRepository.existsByName(TEAM_3_NAME)).thenReturn(false);
        when(mapperService.toTeamEntity(createTeamRequest)).thenReturn(teamEntity);
        when(teamRepository.save(teamEntity)).thenReturn(teamEntitySaved);
        when(mapperService.toTeam(teamEntitySaved)).thenReturn(team);

        Team createdTeam = teamService.createTeam(createTeamRequest);

        assertEquals(TEAM_3_NAME, createdTeam.getName());
    }

    @Test
    public void testCreateTeam_nameAlreadyExists_badRequest() {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest(TEAM_3_NAME);
        when(teamRepository.existsByName(TEAM_3_NAME)).thenReturn(true);

        DocAnalyzerException exception = assertThrows(DocAnalyzerException.class, () -> teamService.createTeam(createTeamRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Team name " + TEAM_3_NAME + " already exists", exception.getMessage());
    }

    @Test
    public void testCreateTeam_nameIsEmpty_badRequest() {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest(" ");
        when(teamRepository.existsByName(" ")).thenReturn(false);

        DocAnalyzerException exception = assertThrows(DocAnalyzerException.class, () -> teamService.createTeam(createTeamRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Team name cannot be empty or null", exception.getMessage());
    }

    @Test
    public void testCreateTeam_nameIsNull_badRequest() {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest(null);
        when(teamRepository.existsByName(null)).thenReturn(false);

        DocAnalyzerException exception = assertThrows(DocAnalyzerException.class, () -> teamService.createTeam(createTeamRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Team name cannot be empty or null", exception.getMessage());
    }

    @Test
    public void testCreateTeam_nameIsTooLong_badRequest() {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest(LONG_TEAM_NAME);
        when(teamRepository.existsByName(LONG_TEAM_NAME)).thenReturn(false);

        DocAnalyzerException exception = assertThrows(DocAnalyzerException.class, () -> teamService.createTeam(createTeamRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Team name must not exceed 255 characters", exception.getMessage());
    }

}
