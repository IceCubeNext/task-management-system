package ru.nextcloudnext.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nextcloudnext.dto.NewUserDto;
import ru.nextcloudnext.dto.UserDto;
import ru.nextcloudnext.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target="fullName", expression="java(user.getLastname() +  \" \" + user.getFirstname().charAt(0)  +  \".\" + user.getPatronymic().charAt(0)  +  \".\")")
    UserDto toDto(User user);

    User toModel(NewUserDto userDto);
}
