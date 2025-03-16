package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.response.LogoutResponseDTO;


public interface LogoutService {

    LogoutResponseDTO logoutById(Long userId);
}
