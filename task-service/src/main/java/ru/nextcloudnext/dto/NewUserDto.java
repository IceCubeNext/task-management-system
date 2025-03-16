package ru.nextcloudnext.dto;

import lombok.*;
import ru.nextcloudnext.model.Marker.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewUserDto {
    @NotNull(message = "{id should not be null}", groups = OnCreate.class)
    @PositiveOrZero(message = "{id should be positive or zero}", groups = {OnCreate.class, OnUpdate.class})
    private Long id;
    @NotBlank(message = "{firstname should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 255, message = "{firstname field's size should be from 1 to 255 letters}", groups = {OnCreate.class, OnUpdate.class})
    private String firstname;
    @NotBlank(message = "{lastname should not be blank}", groups = OnCreate.class)
    @Size(min = 1, max = 255, message = "{lastname field's size should be from 1 to 255 letters}", groups = {OnCreate.class, OnUpdate.class})
    private String lastname;
    private String patronymic;
}
