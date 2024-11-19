package org.olhab.springboot.dto.todo;

import lombok.Data;
import org.olhab.springboot.model.TodoPriority;
import org.olhab.springboot.model.TodoStatus;

import java.time.LocalDateTime;

@Data
public class TodoResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TodoPriority priority;
    private TodoStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long userId;
}
