package com.visiblethread.docanalyzer.utils;

import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static com.visiblethread.docanalyzer.utils.Constants.CREATED_AT;

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
}
