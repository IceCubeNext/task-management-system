package ru.nextcloudnext.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String accessToken;
    private String refreshToken;
}
