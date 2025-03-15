package ru.nextcloudnext.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskSearchParameters {
    private Long authorId;
    private Long performerId;
    private String searchValue;
}
