package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team toTeam(TeamEntity teamEntity);

}
