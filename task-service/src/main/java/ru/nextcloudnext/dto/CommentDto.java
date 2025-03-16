package ru.nextcloudnext.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Сущность комментария (response)")
public class CommentDto {
    @Schema(description = "Идентификатор комментария", example = "1")
    private Long id;
    @Schema(description = "Заголовок", example = "Просмотрел, написал в описании")
    private String title;
    @Schema(description = "Описание", example = "Нужно исправить кое-что....")
    private String description;
    @Schema(description = "Автор комментария")
    private UserDto author;
}
