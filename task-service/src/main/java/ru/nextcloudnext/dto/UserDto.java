package ru.nextcloudnext.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String fullName;
}
