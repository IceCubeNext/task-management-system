package ru.nextcloudnext.mapper;

import org.mapstruct.Mapper;
import ru.nextcloudnext.dto.CommentDto;
import ru.nextcloudnext.dto.NewCommentDto;
import ru.nextcloudnext.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(Comment comment);

    Comment toModel(NewCommentDto commentDto);
}
