package ru.nextcloudnext.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nextcloudnext.dto.NewUserDto;
import ru.nextcloudnext.dto.UserDto;
import ru.nextcloudnext.model.Marker.*;
import ru.nextcloudnext.service.UserService;

import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Hidden
@Tag(name = "Пользователи", description = "Работа с пользователями. Внимание! Предназначен только для внутреннего использования web-интерфейсом в учебных целях. Напрямую вносить изменения запрещено. Данные синхронизируются с другим микросервисом автоматически.")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Получение списка пользователей",
            description = "Получение списка пользователей"
    )
    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Get users");
        return userService.getUsers();
    }

    @Operation(
            summary = "Получение данных пользователя",
            description = "Получение данных пользователя по id"
    )
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        log.info("Get user with id: {}", id);
        return userService.getUser(id);
    }

    @Operation(
            summary = "Добавление пользователя",
            description = "Добавление данных пользователя"
    )
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Validated({OnCreate.class}) NewUserDto userDto) {
        log.info("Post user={}", userDto);
        return userService.addUser(userDto);
    }

    @Operation(
            summary = "Обновление пользователя",
            description = "Обновление данных пользователя по id"
    )
    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody @Validated({OnUpdate.class}) NewUserDto userDto) {
        log.info("Patch user={} with id={}", userDto, id);
        return userService.updateUser(id, userDto);
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Удаление данных пользователя по id"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        log.info("Delete user with id={}", id);
        userService.deleteUser(id);
    }
}
