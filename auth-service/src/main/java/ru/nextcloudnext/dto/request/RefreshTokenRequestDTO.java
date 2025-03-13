package ru.nextcloudnext.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RefreshTokenRequestDTO {
    @NotNull(message = "{refreshToken should not be null}")
    private String refreshToken;
}
