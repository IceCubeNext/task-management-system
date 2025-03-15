package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.*;
import ru.nextcloudnext.model.Comment;

import java.util.List;

public interface CommentService {
    Comment findById(Long id);

    List<CommentDto> getComments(Integer skip, Integer take, Long taskId);

    CommentDto addComment(Long taskId, NewCommentDto commentDto);

    CommentDto updateComment(Long id, Long taskId, NewCommentDto commentDto);

    void deleteComment(Long taskId, Long id);
}
