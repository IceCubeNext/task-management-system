package ru.nextcloudnext.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.nextcloudnext.dto.*;
import ru.nextcloudnext.exception.NotFoundException;
import ru.nextcloudnext.jwt.UserDetailsImpl;
import ru.nextcloudnext.mapper.CommentMapper;
import ru.nextcloudnext.model.Comment;
import ru.nextcloudnext.model.Task;
import ru.nextcloudnext.repository.CommentRepository;
import ru.nextcloudnext.service.CommentService;
import ru.nextcloudnext.service.TaskService;
import ru.nextcloudnext.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserService userService;
    private final TaskService taskService;
    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getComments(Integer skip, Integer take, Long taskId) {
        taskService.getTaskById(taskId);
        if (take == null || skip == null) {
            return repository.findAllByTaskId(taskId).stream().map(mapper::toDto).collect(Collectors.toList());
        } else {
            PageRequest page = PageRequest.of(skip > 0 ? skip / take : 0, take);
            return repository.findAllByTaskId(taskId, page).stream().map(mapper::toDto).collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public CommentDto addComment(Long taskId, NewCommentDto commentDto) {
        UserDetailsImpl details = getUserDetails();
        Task task = taskService.findById(taskId);
        checkCommentAddPermissions(task);
        Comment comment = mapper.toModel(commentDto);
        comment.setAuthor(userService.findById(details.getId()));
        comment.setTask(task);
        return mapper.toDto(repository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long taskId, Long id, NewCommentDto commentDto) {
        Comment comment = isCommentBelongToTask(taskId, id);
        checkCommentChangePermissions(comment);
        if (StringUtils.hasText(commentDto.getTitle()) && !commentDto.getTitle().equals(comment.getTitle())) {
            comment.setTitle(commentDto.getTitle());
        }

        if (StringUtils.hasText(commentDto.getDescription()) && !commentDto.getDescription().equals(comment.getDescription())) {
            comment.setDescription(commentDto.getDescription());
        }
        return mapper.toDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long taskId, Long id) {
        Comment comment = isCommentBelongToTask(taskId, id);
        checkCommentChangePermissions(comment);
        repository.delete(isCommentBelongToTask(taskId, id));
    }

    @Transactional(readOnly = true)
    public Comment isCommentBelongToTask(Long taskId, Long commentId) {
        Comment comment = findById(commentId);
        taskService.findById(taskId);
        if (!Objects.equals(comment.getTask().getId(), taskId)) {
            throw new IllegalArgumentException(
                    String.format("Comment with id=%d does not belong to task with id=%d", commentId, taskId)
            );
        }
        return comment;
    }

    private void checkCommentChangePermissions(Comment comment) {
        UserDetailsImpl details = getUserDetails();
        if (details.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            if (!Objects.equals(details.getId(), comment.getAuthor().getId())) {
                throw new AuthorizationDeniedException("Access denied. You do not have permission");
            }
        }
    }

    private void checkCommentAddPermissions(Task task) {
        UserDetailsImpl details = getUserDetails();
        if (details.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                && !Objects.equals(details.getId(), task.getPerformer().getId())) {
            throw new AuthorizationDeniedException("Access denied. You do not have permission");
        }
    }

    private UserDetailsImpl getUserDetails() {
        UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (details == null) {
            throw new AuthorizationDeniedException("Access denied. Are you anonymous user?");
        }
        return details;
    }
}
