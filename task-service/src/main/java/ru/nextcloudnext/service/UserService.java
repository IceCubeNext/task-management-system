package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.NewUserDto;
import ru.nextcloudnext.dto.UserDto;
import ru.nextcloudnext.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);

    List<UserDto> getUsers();

    UserDto getUser(Long id);

    UserDto addUser(NewUserDto userDto);

    UserDto updateUser(Long id, NewUserDto userDto);

    void deleteUser(Long userId);
}
