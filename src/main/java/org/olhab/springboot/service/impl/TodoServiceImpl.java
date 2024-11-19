package org.olhab.springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.olhab.springboot.dto.taskhistory.TaskHistoryCreateDto;
import org.olhab.springboot.dto.todo.TodoCreateDto;
import org.olhab.springboot.dto.todo.TodoResponseDto;
import org.olhab.springboot.dto.todo.TodoUpdateDto;
import org.olhab.springboot.mapper.TodoMapper;
import org.olhab.springboot.model.Todo;
import org.olhab.springboot.repository.TodoRepository;
import org.olhab.springboot.service.TaskHistoryService;
import org.olhab.springboot.service.TodoService;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final TaskHistoryService taskHistoryService;

    @Override
    @Transactional
    public TodoResponseDto save(TodoCreateDto todoCreateDto) {
        Todo entity = todoMapper.toModelFromCreateDto(todoCreateDto);
        return todoMapper.toDto(todoRepository.save(entity));
    }

    @Override
    @Transactional
    public TodoResponseDto update(TodoUpdateDto todoUpdateDto, Long id) throws JsonProcessingException {
        Todo entity = todoMapper.toModelFromUpdateDto(todoUpdateDto);
        entity.setId(id);

        TodoResponseDto todoBefore = this.getById(id);
        TaskHistoryCreateDto taskHistoryCreateDto = new TaskHistoryCreateDto();
        taskHistoryCreateDto.setTodoId(id);
        taskHistoryCreateDto.setChangeDate(LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();
        taskHistoryCreateDto.setOldState(objectMapper.writeValueAsString(todoBefore));
        taskHistoryCreateDto.setNewState(objectMapper.writeValueAsString(entity));
        taskHistoryCreateDto.setChangedBy("Admin");
        taskHistoryService.save(taskHistoryCreateDto);

        return todoMapper.toDto(todoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponseDto getById(Long id) {
        return todoMapper.toDto(todoRepository.getById(id));
    }
}
