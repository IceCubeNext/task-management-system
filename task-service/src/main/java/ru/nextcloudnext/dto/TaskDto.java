package ru.nextcloudnext.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.nextcloudnext.model.enums.Priority;
import ru.nextcloudnext.model.enums.Status;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Сущность задачи (response)")
public class TaskDto {
    @Schema(description = "Идентификатор задачи", example = "1")
    private Long id;
    @Schema(description = "Заголовок", example = "Задача 1")
    private String title;
    @Schema(description = "Описание", example = "Нужно сделвть что-то.....")
    private String description;
    @Schema(description = "Статус")
    private Status status;
    @Schema(description = "Приоритет")
    private Priority priority;
    @Schema(description = "Автор задачи")
    private UserDto author;
    @Schema(description = "Исполнитель задачи")
    private UserDto performer;
    @Schema(description = "Список комментариев")
    private List<CommentDto> comments;
}
