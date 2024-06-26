package com.dhbw.get2gether.backend.user.application.mapper;

import com.dhbw.get2gether.backend.user.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User mapToUser(CreateUserCommand command);


    User mapSyncCommandToUser(@MappingTarget User user, SyncUserCommand command);

    User mapSettingsCommandToUser(@MappingTarget User user, UpdateUserCommand command);

    SimpleUserDto mapToSimpleUserDto(User user);
}
