package ru.nextcloudnext.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenResponseDTO {
    private String accessToken;
    private String refreshToken;
}
