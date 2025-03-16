package ru.nextcloudnext.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Сущность ответа выдачи новой пары токенов")
public class TokenResponseDTO {
    @Schema(description = "Аксес токен", example = "dgfdfhgfjdlsfgkdhg...")
    private String accessToken;
    @Schema(description = "Рефреш токен", example = "dgfdfhgfjdlsfgkdhg...")
    private String refreshToken;
}
