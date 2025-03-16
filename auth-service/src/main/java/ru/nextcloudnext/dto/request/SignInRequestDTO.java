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
@Schema(description = "Сущность запроса аутентификации")
public class SignInRequestDTO {
    @Schema(description = "Логин", example = "admin")
    @NotNull(message = "{login should not be null}")
    private String login;
    @Schema(description = "Пароль", example = "admin")
    @NotNull(message = "{password should not be null}")
    private char[] password;
}
