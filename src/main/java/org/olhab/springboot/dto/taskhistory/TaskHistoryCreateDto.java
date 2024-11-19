package org.olhab.springboot.dto.taskhistory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskHistoryCreateDto {
    @NotNull(message = "Todo id is required")
    private Long todoId;
    @NotBlank(message = "Old state can't be empty")
    private String oldState;
    @NotBlank(message = "New state can't be empty")
    private String newState;
    private LocalDateTime changeDate;
    private String changedBy;
}
