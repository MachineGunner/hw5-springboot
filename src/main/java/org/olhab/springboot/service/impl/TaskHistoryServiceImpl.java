package org.olhab.springboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.olhab.springboot.dto.taskhistory.TaskHistoryCreateDto;
import org.olhab.springboot.dto.taskhistory.TaskHistoryResponseDto;
import org.olhab.springboot.mapper.TaskHistoryMapper;
import org.olhab.springboot.model.TaskHistory;
import org.olhab.springboot.repository.TaskHistoryRepository;
import org.olhab.springboot.service.TaskHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskHistoryServiceImpl implements TaskHistoryService {
    private final TaskHistoryRepository taskHistoryRepository;
    private final TaskHistoryMapper taskHistoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponseDto> getAllForTodoId(Long todoId) {
        return taskHistoryRepository.findByTodoId(todoId).stream().map(taskHistoryMapper::toDto).toList();
    }

    @Override
    @Transactional
    public TaskHistoryResponseDto save(TaskHistoryCreateDto taskHistoryCreateDto) {
        TaskHistory entity = taskHistoryMapper.toModelFromCreateDto(taskHistoryCreateDto);
        return taskHistoryMapper.toDto(taskHistoryRepository.save(entity));
    }
}
