package ru.nextcloudnext.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDto {
    private Long id;
    private String title;
    private String description;
    private UserDto author;
}
