package ru.nextcloudnext.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.nextcloudnext.dto.NewUserDto;
import ru.nextcloudnext.dto.UserDto;
import ru.nextcloudnext.exception.NotFoundException;
import ru.nextcloudnext.mapper.UserMapper;
import ru.nextcloudnext.model.User;
import ru.nextcloudnext.repository.UserRepository;
import ru.nextcloudnext.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


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
    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    @Transactional
    public UserDto addUser(NewUserDto userDto) {
        User user = mapper.toModel(userDto);
        return mapper.toDto(repository.save(user));
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, NewUserDto userDto) {
        User user = findById(id);
        if (StringUtils.hasText(userDto.getFirstname()) && !userDto.getFirstname().equals(user.getFirstname())) {
            user.setFirstname(userDto.getFirstname());
        }
        if (StringUtils.hasText(userDto.getLastname()) && !userDto.getLastname().equals(user.getLastname())) {
            user.setLastname(userDto.getLastname());
        }
        if (StringUtils.hasText(userDto.getPatronymic()) && !userDto.getPatronymic().equals(user.getPatronymic())) {
            user.setPatronymic(userDto.getPatronymic());
        }
        return mapper.toDto(repository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        findById(userId);
        repository.deleteById(userId);
    }
}
