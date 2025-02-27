package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.CreateTeamRequest;
import com.visiblethread.docanalyzer.model.CreateUserRequest;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MapperService {

    Team toTeam(TeamEntity teamEntity);

    @Mapping(target = "id", ignore = true)
    TeamEntity toTeamEntity(CreateTeamRequest createTeamRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teams", ignore = true)
    UserEntity toUserEntity(CreateUserRequest createUserRequest);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "teams", source = "teams")
    User toUser(UserEntity userEntity);

}
