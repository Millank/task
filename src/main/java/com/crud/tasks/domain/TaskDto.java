package com.crud.tasks.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TaskDto {
    private Long id;
    private String title;
    private String content;
}
