package ru.nextcloudnext.mapper;

import org.mapstruct.Mapper;
import ru.nextcloudnext.dto.NewUserDto;
import ru.nextcloudnext.dto.UserDto;
import ru.nextcloudnext.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(NewUserDto userDto);
}
