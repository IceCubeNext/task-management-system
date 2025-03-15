package ru.nextcloudnext.dto;

import lombok.*;
import ru.nextcloudnext.model.enums.Priority;
import ru.nextcloudnext.model.enums.Status;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserDto author;
    private UserDto performer;
    private List<CommentDto> comments;
}
