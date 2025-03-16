package ru.nextcloudnext.controller;

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
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{taskId}/comments")
    public List<CommentDto> getCommentsByTaskId(@PathVariable Long taskId,
                                                @RequestParam(required = false) Integer skip,
                                                @RequestParam(required = false) Integer take) {
        log.info("Get comments from task with id={} skip={} take={}", taskId, skip, take);
        return commentService.getComments(skip, take, taskId);
    }

    @PostMapping("/{taskId}/comments")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable Long taskId,
                                 @RequestBody @Validated({OnCreate.class}) NewCommentDto commentDto) {
        log.info("Post comment under task with id={}, comment={}", taskId, commentDto);
        return commentService.addComment(taskId, commentDto);
    }

    @PatchMapping("/{taskId}/comments/{id}")
    public CommentDto updateComment(@PathVariable Long taskId,
                                    @PathVariable Long id,
                                    @RequestBody @Validated({OnUpdate.class}) NewCommentDto commentDto) {
        log.info("Patch comment with id={} under task with id={}, dto={}", id, taskId, commentDto);
        return commentService.updateComment(taskId, id, commentDto);
    }

    @DeleteMapping("/{taskId}/comments/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long taskId,
                              @PathVariable Long id) {
        log.info("Delete comment with id={} under task with id={}", id, taskId);
        commentService.deleteComment(taskId, id);
    }
}
