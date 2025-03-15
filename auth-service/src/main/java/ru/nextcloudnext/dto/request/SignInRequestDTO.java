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
public class SignInRequestDTO {
    @NotNull(message = "{login should not be null}")
    private String login;
    @NotNull(message = "{password should not be null}")
    private char[] password;
}
