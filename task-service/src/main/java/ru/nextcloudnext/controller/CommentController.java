package ru.nextcloudnext.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nextcloudnext.dto.CommentDto;
import ru.nextcloudnext.dto.NewCommentDto;
import ru.nextcloudnext.model.Marker.*;
import ru.nextcloudnext.service.CommentService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "Комментарии", description = "Работа с комментариями задачи")
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "Получение комментарив к задаче",
            description = "Получение комментариев к задаче по ее id"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{taskId}/comments")
    public List<CommentDto> getCommentsByTaskId(
            @Parameter(description = "Идентификатор задачи", required = true, example = "1")
            @PathVariable Long taskId,
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(required = false) Integer skip,
            @Parameter(description = "Количество элементов на странице", example = "10")
            @RequestParam(required = false) Integer take) {
        log.info("Get comments from task with id={} skip={} take={}", taskId, skip, take);
        return commentService.getComments(skip, take, taskId);
    }

    @Operation(
            summary = "Добавление комментария",
            description = "Добавление комментария к задаче. Для добавления необходимо быть автором задачи либо администратором"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/{taskId}/comments")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentDto addComment(
            @Parameter(description = "Идентификатор задачи", required = true, example = "1")
            @PathVariable Long taskId,
            @RequestBody @Validated({OnCreate.class}) NewCommentDto commentDto) {
        log.info("Post comment under task with id={}, comment={}", taskId, commentDto);
        return commentService.addComment(taskId, commentDto);
    }

    @Operation(
            summary = "Обновление комментария",
            description = "Обновление комментария к задаче. Для обновления необходимо быть автором задачи либо администратором"
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{taskId}/comments/{id}")
    public CommentDto updateComment(
            @Parameter(description = "Идентификатор задачи", required = true, example = "1")
            @PathVariable Long taskId,
            @Parameter(description = "Идентификатор комментария", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody @Validated({OnUpdate.class}) NewCommentDto commentDto) {
        log.info("Patch comment with id={} under task with id={}, dto={}", id, taskId, commentDto);
        return commentService.updateComment(taskId, id, commentDto);
    }

    @Operation(
            summary = "Удаление комментария",
            description = "Удаление комментария к задаче. Для удаления необходимо быть автором задачи либо администратором"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{taskId}/comments/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteComment(
            @Parameter(description = "Идентификатор задачи", required = true, example = "1")
            @PathVariable Long taskId,
            @Parameter(description = "Идентификатор комментария", required = true, example = "1")
            @PathVariable Long id) {
        log.info("Delete comment with id={} under task with id={}", id, taskId);
        commentService.deleteComment(taskId, id);
    }
}
