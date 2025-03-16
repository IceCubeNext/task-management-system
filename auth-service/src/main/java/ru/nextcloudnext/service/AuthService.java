package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO signIn(String login, char[] password);

    AuthResponseDTO signUp(String login, char[] password, String firstName, String lastName, String patronymic);
}
