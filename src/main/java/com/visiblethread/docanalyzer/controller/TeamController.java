package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.service.TeamService;
import com.visiblethread.docanalyzer.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.visiblethread.docanalyzer.api.TeamsApi;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class TeamController implements TeamsApi {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @Override
    public ResponseEntity<List<Team>> getAllTeams() {
        logger.debug("Received request to get all teams");
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamRequest createTeamRequest) {
        logger.debug("Received request to create a team");
        return new ResponseEntity<>(teamService.createTeam(createTeamRequest), HttpStatus.CREATED);
    }
}
