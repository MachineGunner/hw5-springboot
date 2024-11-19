package org.olhab.springboot.service;

import org.olhab.springboot.dto.taskhistory.TaskHistoryCreateDto;
import org.olhab.springboot.dto.taskhistory.TaskHistoryResponseDto;

import java.util.List;

public interface TaskHistoryService {
    List<TaskHistoryResponseDto> getAllForTodoId(Long todoId);

    TaskHistoryResponseDto save(TaskHistoryCreateDto taskHistoryCreateDto);
}
