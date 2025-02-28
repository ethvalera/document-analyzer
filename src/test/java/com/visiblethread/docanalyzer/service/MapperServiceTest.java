package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.*;
import com.visiblethread.docanalyzer.persistence.entity.DocumentEntity;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MapperServiceTest {

    @Autowired
    private MapperService mapperService;

    @Test
    public void testTeamEntityToTeam() {
        TeamEntity teamEntity = createTeamEntityWithName(TEAM_1_NAME);
        Team team = mapperService.toTeam(teamEntity);

        assertEquals(teamEntity.getName(), team.getName());
        assertEquals(teamEntity.getId(), team.getId());
    }

    @Test
    public void testCreateTeamRequestToTeamEntity() {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest(TEAM_1_NAME);
        TeamEntity teamEntity = mapperService.toTeamEntity(createTeamRequest);

        assertEquals(createTeamRequest.getName(), teamEntity.getName());
    }

    @Test
    public void testCreateUserRequestToUserEntity() {
        CreateUserRequest createTeamRequest = new CreateUserRequest(EMAIL_USER_1, List.of(TEAM_1_NAME, TEAM_2_NAME));
        UserEntity userEntity = mapperService.toUserEntity(createTeamRequest);

        assertNull(userEntity.getId());
        assertEquals(createTeamRequest.getEmail(), userEntity.getEmail());
        assertNull(userEntity.getCreatedAt());
        assertTrue(userEntity.getTeams().isEmpty());
    }

    @Test
    public void testUserEntityToUser() {
        UserEntity userEntity = createUserEntityWithEmailAndTeams(EMAIL_USER_1, new HashSet<>());
        userEntity.setId(1L);
        userEntity.setCreatedAt(Instant.parse(CREATED_AT));

        TeamEntity team1 = createTeamEntityWithIdAndName(TEAM_1_ID, TEAM_1_NAME);
        TeamEntity team2 = createTeamEntityWithIdAndName(TEAM_2_ID, TEAM_2_NAME);
        userEntity.getTeams().addAll(Set.of(team1, team2));

        User user = mapperService.toUser(userEntity);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getCreatedAt(), user.getCreatedAt());
        assertEquals(2, user.getTeams().size());
    }

    @Test
    public void testDocumentEntityToDocument() {
        UserEntity userEntity = createUserEntityWithEmailAndTeams(EMAIL_USER_1, new HashSet<>());
        userEntity.setId(1L);
        userEntity.setCreatedAt(Instant.parse(CREATED_AT));
        DocumentEntity documentEntity = createDocumentEntityWithAllParams(DOC_1_NAME, DOC_1_COUNT, userEntity);
        Document document = mapperService.toDocument(documentEntity);

        assertEquals(documentEntity.getName(), document.getName());
        assertEquals(documentEntity.getWordCount(), document.getWordCount());
        assertEquals(documentEntity.getId(), document.getId());
        assertEquals(documentEntity.getUploadedAt(), document.getUploadedAt());
        assertEquals(documentEntity.getUserEntity().getId(), document.getUserId());
    }

}
