package ru.nextcloudnext.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "Сущность ответа выхода из системы")
public class LogoutResponseDTO {
    @Schema(description = "Сообщение", example = "User with id '1' is successfully logged off")
    private String logoutMessage;
}
