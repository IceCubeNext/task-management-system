package ru.nextcloudnext.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.nextcloudnext.model.Marker.*;
import ru.nextcloudnext.model.enums.Priority;
import ru.nextcloudnext.model.enums.Status;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Сущность задачи (request)")
public class NewTaskDto {
    @Schema(description = "Заголовок", example = "Задача 1")
    @NotBlank(message = "{title should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 255, groups = {OnCreate.class, OnUpdate.class}, message = "{title size should be from 1 to 255 letters}")
    private String title;
    @Schema(description = "Описание", example = "Нужно сделвть что-то.....")
    @NotBlank(message = "{description should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 5000, groups = {OnCreate.class, OnUpdate.class}, message = "{description size should be from 1 to 5000 letters}")
    private String description;
    @Schema(description = "Статус")
    @NotNull(message = "{status should not be null}", groups = OnCreate.class)
    private Status status;
    @Schema(description = "Приоритет")
    @NotNull(message = "{priority should not be null}", groups = OnCreate.class)
    private Priority priority;
    @Schema(description = "Исполнитель задачи")
    @NotNull(message = "{performer should not be null}", groups = OnCreate.class)
    private UserDto performer;
}
