package com.visiblethread.docanalyzer.utils;

import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;

public final class TestDataUtils {

    private TestDataUtils(){}

    public static TeamEntity createTeamEntityWithName(String name) {
        TeamEntity teamEntity = new TeamEntity();
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
}
