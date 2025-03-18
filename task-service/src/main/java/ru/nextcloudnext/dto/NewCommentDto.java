package ru.nextcloudnext.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.nextcloudnext.model.Marker.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Сущность комментария (request)")
public class NewCommentDto {
    @Schema(description = "Заголовок", example = "Просмотрел, написал в описании")
    @NotBlank(message = "{title should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 255, groups = {OnCreate.class, OnUpdate.class}, message = "{title size should be from 1 to 255 letters}")
    private String title;
    @Schema(description = "Описание", example = "Нужно исправить кое-что....")
    @NotBlank(message = "{title should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 5000, groups = {OnCreate.class, OnUpdate.class}, message = "{description size should be from 1 to 5000 letters}")
    private String description;
}
