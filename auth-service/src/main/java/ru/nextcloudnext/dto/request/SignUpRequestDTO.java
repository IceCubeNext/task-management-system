package ru.nextcloudnext.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "Сущность запроса авторизации")
public class SignUpRequestDTO {
    @Schema(description = "Логин", example = "admin")
    @NotNull(message = "{login should not be null}")
    private String login;
    @Schema(description = "Пароль", example = "admin")
    @NotNull(message = "{password should not be null}")
    private char[] password;
    @Schema(description = "Имя", example = "Иван")
    @NotNull(message = "{firstName should not be null}")
    private String firstName;
    @Schema(description = "Фамилия", example = "Иванов")
    @NotNull(message = "{lastName should not be null}")
    private String lastName;
    @Schema(description = "Отчество", example = "Иванович")
    @NotNull(message = "{patronymic should not be null}")
    private String patronymic;
}
