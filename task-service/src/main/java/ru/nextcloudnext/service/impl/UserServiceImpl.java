package ru.nextcloudnext.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nextcloudnext.dto.NewUserDto;
import ru.nextcloudnext.dto.UserDto;
import ru.nextcloudnext.exception.NotFoundException;
import ru.nextcloudnext.mapper.UserMapper;
import ru.nextcloudnext.model.User;
import ru.nextcloudnext.repository.UserRepository;
import ru.nextcloudnext.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId)));
    }

    @Override
    @Transactional
    public UserDto addUser(NewUserDto userDto) {
        User user = mapper.toModel(userDto);
        return mapper.toDto(repository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        findById(userId);
        repository.deleteById(userId);
    }
}
