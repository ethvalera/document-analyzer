package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Team;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TeamService {

    List<Team> getAllTeams();

}
