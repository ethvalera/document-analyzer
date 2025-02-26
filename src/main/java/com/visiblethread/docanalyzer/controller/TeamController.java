package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.service.TeamService;
import com.visiblethread.docanalyzer.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.visiblethread.docanalyzer.api.TeamsApi;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class TeamController implements TeamsApi {

    @Autowired
    private TeamService teamService;

    @Override
    public ResponseEntity<List<Team>> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }
}
