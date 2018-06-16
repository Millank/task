package com.crud.tasks.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 20)
    @Column(name = "name")
    private String title;

    @Size(max = 150)
    @Column(name = "description")
    private String content;
}
