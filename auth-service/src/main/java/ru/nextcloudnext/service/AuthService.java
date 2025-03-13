package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.response.AuthResponseDTO;

/**
 * @author Vladimir F. 10.01.2023
 */
public interface AuthService {

    AuthResponseDTO signIn(String login, char[] password);

    AuthResponseDTO signUp(String login, char[] password, String firstName, String lastName, String patronymic);
}
