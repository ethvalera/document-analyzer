package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.DuplicateFieldException;
import com.visiblethread.docanalyzer.exception.ValidationFailureException;
import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MapperService mapperService;

    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(mapperService::toTeam)
                .collect(Collectors.toList());
    }

    @Override
    public Team createTeam(CreateTeamRequest createTeamRequest) {
        final String teamName = createTeamRequest.getName();
        logger.debug("Creating team with name: {}", teamName);
        validateTeamName(teamName);
        TeamEntity teamEntityCreated = teamRepository.save(mapperService.toTeamEntity(createTeamRequest));
        return mapperService.toTeam(teamEntityCreated);
    }

    private void validateTeamName(String name) {
        logger.debug("Validating team with name: {}", name);
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
