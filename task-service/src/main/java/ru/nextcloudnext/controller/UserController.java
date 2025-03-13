package ru.nextcloudnext.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.nextcloudnext.dto.NewUserDto;
import ru.nextcloudnext.dto.UserDto;
import ru.nextcloudnext.service.UserService;

import javax.validation.Valid;


@Slf4j
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/admin/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody NewUserDto userDto) {
        log.info("Post user={} by admin", userDto);
        return userService.addUser(userDto);
    }

    @DeleteMapping("/admin/users/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        log.info("Delete user with id={} by admin", userId);
        userService.deleteUser(userId);
    }

}
