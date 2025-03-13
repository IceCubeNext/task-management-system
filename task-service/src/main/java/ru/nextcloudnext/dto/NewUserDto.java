package ru.nextcloudnext.dto;

import lombok.*;

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
    @NotNull(message = "{id should not be null}")
    @PositiveOrZero(message = "{id should be positive or zero}")
    private Long id;
    @NotBlank
    @Size(min = 1, max = 255, message = "{firstname field's size should be from 1 to 255 letters}")
    private String firstname;
    @NotBlank
    @Size(min = 1, max = 255, message = "{lastname field's size should be from 1 to 255 letters}")
    private String lastname;
    private String patronymic;
}
