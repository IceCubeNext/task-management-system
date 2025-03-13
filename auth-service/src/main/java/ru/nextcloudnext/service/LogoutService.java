package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.response.LogoutResponseDTO;

/**
 * @author Vladimir F. 21.02.2023
 */
public interface LogoutService {

    LogoutResponseDTO logoutById(Long userId);
}
