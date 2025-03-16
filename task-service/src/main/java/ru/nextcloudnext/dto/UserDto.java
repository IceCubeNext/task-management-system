package ru.nextcloudnext.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Сущность пользователя (response)")
public class UserDto {
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;
    @Schema(description = "Имя", example = "Иван")
    private String firstname;
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastname;
    @Schema(description = "Отчество", example = "Иванович")
    private String patronymic;
    @Schema(description = "Полное имя", example = "Иванов И.И.")
    private String fullName;
}
