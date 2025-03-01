package com.visiblethread.docanalyzer.utils;

import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.persistence.entity.DocumentEntity;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static com.visiblethread.docanalyzer.utils.TestConstants.CREATED_AT;

public final class TestDataUtils {

    private TestDataUtils(){}

    public static TeamEntity createTeamEntityWithName(String name) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setName(name);
        return teamEntity;
    }

    public static TeamEntity createTeamEntityWithIdAndName(Long id, String name) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(id);
        teamEntity.setName(name);
        return teamEntity;
    }

    public static Team createTeamWithName(String name) {
        Team team = new Team();
        team.setName(name);
        return team;
    }

    public static Team createTeamWithIdAndName(Long id, String name) {
        Team team = new Team();
        team.setId(id);
        team.setName(name);
        return team;
    }

    public static UserEntity createUserEntityWithEmailAndTeams(String email, Set<TeamEntity> teams) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setTeams(teams);
        return userEntity;
    }

    public static User createUserWithEmailAndTeams(String email, List<Team> teams) {
        User user = new User();
        user.setEmail(email);
        user.setCreatedAt(Instant.parse(CREATED_AT));
        user.setTeams(teams);
        return user;
    }

    public static DocumentEntity createDocumentEntityWithAllParams(String name, Integer count, UserEntity userEntity) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setName(name);
        documentEntity.setWordCount(count);
        documentEntity.setUploadedAt(Instant.parse(CREATED_AT));
        documentEntity.setUserEntity(userEntity);
        return documentEntity;
    }

    public static Document createDocument(String name, Integer count, Long userId) {
        Document document = new Document();
        document.setName(name);
        document.setWordCount(count);
        document.setUploadedAt(Instant.parse(CREATED_AT));
        document.setUserId(userId);
        return document;
    }
}
