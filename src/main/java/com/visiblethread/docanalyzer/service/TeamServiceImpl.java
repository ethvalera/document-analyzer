package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toTeam)
                .collect(Collectors.toList());
    }
}
