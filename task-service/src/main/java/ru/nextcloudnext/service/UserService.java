package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.NewUserDto;
import ru.nextcloudnext.dto.UserDto;
import ru.nextcloudnext.model.User;

public interface UserService {
    User findById(Long id);

    UserDto addUser(NewUserDto userDto);

    void deleteUser(Long userId);
}
