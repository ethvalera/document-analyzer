package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team toTeam(TeamEntity teamEntity);

    @Mapping(target = "id", ignore = true)
    TeamEntity toTeamEntity(CreateTeamRequest createTeamRequest);

}
