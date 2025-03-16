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
@Schema(description = "Сущность запроса пары токенов")
public class RefreshTokenRequestDTO {
    @Schema(description = "Рефреш токен", example = "dgfdfhgfjdlsfgkdhg...")
    @NotNull(message = "{refreshToken should not be null}")
    private String refreshToken;
}
