package com.veri.taskmanager.dto;

import com.veri.taskmanager.entity.Task;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Task.Status status;
}
