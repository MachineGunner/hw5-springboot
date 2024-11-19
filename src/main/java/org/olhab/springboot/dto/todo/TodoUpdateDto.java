package org.olhab.springboot.dto.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.olhab.springboot.model.TodoPriority;
import org.olhab.springboot.model.TodoStatus;

import java.time.LocalDateTime;

@Data
public class TodoUpdateDto {
    @NotBlank(message = "Title can't be empty")
    @Size(max = 100, message = "Title must be no longer than {max} symbols")
    private String title;
    @Size(max = 500, message = "Description must be no longer than {max} symbols")
    private String description;
    @NotNull(message = "Due date can't be empty")
    private LocalDateTime dueDate;
    private TodoPriority priority;
    private TodoStatus status;
}
