package ru.nextcloudnext.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Сущность ответа аутентификации")
public class AuthResponseDTO {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Логин", example = "admin")
    private String login;
    @Schema(description = "Имя", example = "Иван")
    private String firstName;
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;
    @Schema(description = "Отчество", example = "Иванович")
    private String patronymic;
    @Schema(description = "Аксес токен", example = "dgfdfhgfjdlsfgkdhg...")
    private String accessToken;
    @Schema(description = "Рефреш токен", example = "dgfdfhgfjdlsfgkdhg...")
    private String refreshToken;
}
