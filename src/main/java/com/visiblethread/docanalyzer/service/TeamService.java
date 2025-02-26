package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.model.Team;

import java.util.List;

public interface TeamService {

    List<Team> getAllTeams();

    Team createTeam(CreateTeamRequest createTeamRequest);

}
