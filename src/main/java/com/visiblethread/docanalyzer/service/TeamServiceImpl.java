package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.DuplicateFieldException;
import com.visiblethread.docanalyzer.exception.ValidationFailureException;
import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
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
    private MapperService mapperService;

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(mapperService::toTeam)
                .collect(Collectors.toList());
    }

    @Override
    public Team createTeam(CreateTeamRequest createTeamRequest) {
        validateTeamName(createTeamRequest.getName());
        TeamEntity teamEntityCreated = teamRepository.save(mapperService.toTeamEntity(createTeamRequest));
        return mapperService.toTeam(teamEntityCreated);
    }

    private void validateTeamName(String name) {
        if(teamRepository.existsByName(name)){
            throw new DuplicateFieldException("team name", name);
        }
        if(name == null || name.trim().isEmpty()) {
            throw new ValidationFailureException("Team name cannot be empty or null");
        }
        if (name.length() > 255) {
            throw new ValidationFailureException("Team name must not exceed 255 characters");
        }
    }
}
