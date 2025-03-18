package ru.nextcloudnext.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.nextcloudnext.dto.*;
import ru.nextcloudnext.exception.NotFoundException;
import ru.nextcloudnext.jwt.UserDetailsImpl;
import ru.nextcloudnext.mapper.CommentMapper;
import ru.nextcloudnext.mapper.TaskMapper;
import ru.nextcloudnext.model.Comment;
import ru.nextcloudnext.model.Task;
import ru.nextcloudnext.repository.CommentRepository;
import ru.nextcloudnext.repository.TaskRepository;
import ru.nextcloudnext.service.TaskService;
import ru.nextcloudnext.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final UserService userService;
    private final TaskRepository repository;
    private final CommentRepository commentRepository;
    private final TaskMapper mapper;
    private final CommentMapper commentMapper;

    @Override
    @PostAuthorize("hasRole('ADMIN') or authentication.name == returnObject.performer.login")
    @Transactional(readOnly = true)
    public Task findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Task with id=%d was not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> getTasks(Integer skip, Integer take, TaskSearchParameters parameters) {
        List<Task> tasks = repository.findTasks(skip, take, parameters);
        Map<Long, List<Comment>> taskComments = commentRepository
                .findAllByTaskIdIn(tasks.stream().map(Task::getId).collect(toList()))
                .stream()
                .collect(Collectors.groupingBy(c ->
                                c.getTask().getId(),
                        toList())
                );
        List<TaskDto> taskDtoList = tasks.stream().map(mapper::toDto).toList();
        for (TaskDto taskDto : taskDtoList) {
            if (taskComments.containsKey(taskDto.getId())) {
                taskDto.setComments(taskComments.get(taskDto.getId()).stream().map(commentMapper::toDto).collect(toList()));
            } else {
                taskDto.setComments(Collections.emptyList());
            }
        }
        return taskDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto getTaskById(Long id) {
        Task task = findById(id);
        TaskDto taskDto = mapper.toDto(task);
        taskDto.setComments(commentRepository.findAllByTaskId(id).stream().map(commentMapper::toDto).collect(toList()));
        return taskDto;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public TaskDto addTask(NewTaskDto taskDto) {
        Task task = mapper.toModel(taskDto);
        UserDetailsImpl details = getUserDetails();
        task.setAuthor(userService.findById(details.getId()));
        task.setPerformer(userService.findById(taskDto.getPerformer().getId()));
        return mapper.toDto(repository.save(task));
    }

    @Override
    @Transactional
    public TaskDto updateTask(Long id, NewTaskDto taskDto) {
        Task task = findById(id);
        UserDetailsImpl details = getUserDetails();
        boolean isAdmin = details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isPerformer = details.getId().equals(task.getPerformer().getId());

        if (isAdmin) {
            if (StringUtils.hasText(taskDto.getTitle()) && !taskDto.getTitle().equals(task.getTitle())) {
                task.setTitle(taskDto.getTitle());
            }
            if (StringUtils.hasText(taskDto.getDescription()) && !taskDto.getDescription().equals(task.getDescription())) {
                task.setDescription(taskDto.getTitle());
            }
            if (taskDto.getPriority() != null) {
                task.setPriority(taskDto.getPriority());
            }
            if (taskDto.getPerformer() != null && !Objects.equals(taskDto.getPerformer().getId(), task.getPerformer().getId())) {
                task.setPerformer(userService.findById(taskDto.getPerformer().getId()));
            }
        }

        if (taskDto.getStatus() != null && (isPerformer || isAdmin)) {
            task.setStatus(taskDto.getStatus());
        }
        TaskDto updatedTask = mapper.toDto(repository.save(task));
        updatedTask.setComments(commentRepository.findAllByTaskId(id).stream().map(commentMapper::toDto).collect(toList()));
        return updatedTask;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTask(Long id) {
        Task task = findById(id);
        repository.delete(task);
    }

    private UserDetailsImpl getUserDetails() {
        UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (details == null) {
            throw new AuthorizationDeniedException("Access denied. Are you anonymous user?");
        }
        return details;
    }
}
