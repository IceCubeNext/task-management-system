package ru.nextcloudnext.dto;

import lombok.*;
import ru.nextcloudnext.model.Marker.*;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewCommentDto {
    @NotBlank(message = "{title should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 255, groups = {OnCreate.class, OnUpdate.class}, message = "{title size should be from 1 to 255 letters}")
    private String title;
    @NotBlank(message = "{title should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 5000, groups = {OnCreate.class, OnUpdate.class}, message = "{description size should be from 1 to 5000 letters}")
    private String description;
    @NotNull(message = "{author should not be null}", groups = OnCreate.class)
    private UserDto author;
}
