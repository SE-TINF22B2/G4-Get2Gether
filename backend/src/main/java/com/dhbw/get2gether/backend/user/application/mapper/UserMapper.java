package com.dhbw.get2gether.backend.user.application.mapper;

import com.dhbw.get2gether.backend.user.model.CreateUserCommand;
import com.dhbw.get2gether.backend.user.model.UpdateUserCommand;
import com.dhbw.get2gether.backend.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User mapToUser(CreateUserCommand command);

    User updateUser(@MappingTarget User user, UpdateUserCommand command);
}
