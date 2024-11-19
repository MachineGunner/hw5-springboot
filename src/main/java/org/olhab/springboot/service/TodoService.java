package org.olhab.springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.olhab.springboot.dto.todo.TodoCreateDto;
import org.olhab.springboot.dto.todo.TodoResponseDto;
import org.olhab.springboot.dto.todo.TodoUpdateDto;

public interface TodoService {
    TodoResponseDto save(TodoCreateDto todoCreateDto);

    TodoResponseDto update(TodoUpdateDto todoCreateDto, Long id) throws JsonProcessingException;

    void deleteById(Long id);

    TodoResponseDto getById(Long id);
}
