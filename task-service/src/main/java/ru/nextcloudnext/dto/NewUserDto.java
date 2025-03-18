package ru.nextcloudnext.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.nextcloudnext.model.Marker.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Сущность пользователя (request)")
public class NewUserDto {
    @Schema(description = "Идентификатор пользователя", example = "1")
    @NotNull(message = "{id should not be null}", groups = OnCreate.class)
    @PositiveOrZero(message = "{id should be positive or zero}", groups = {OnCreate.class, OnUpdate.class})
    private Long id;
    @Schema(description = "Логин пользователя", example = "admin")
    @NotNull(message = "{login should not be null}", groups = OnCreate.class)
    private String login;
    @Schema(description = "Имя", example = "Иван")
    @NotBlank(message = "{firstname should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 255, message = "{firstname field's size should be from 1 to 255 letters}", groups = {OnCreate.class, OnUpdate.class})
    private String firstname;
    @Schema(description = "Фамилия", example = "Иванов")
    @NotBlank(message = "{lastname should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 255, message = "{lastname field's size should be from 1 to 255 letters}", groups = {OnCreate.class, OnUpdate.class})
    private String lastname;
    @Schema(description = "Отчество", example = "Иванович")
    @NotBlank(message = "{patronymic should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 255, message = "{patronymic field's size should be from 1 to 255 letters}", groups = {OnCreate.class, OnUpdate.class})
    private String patronymic;
}
