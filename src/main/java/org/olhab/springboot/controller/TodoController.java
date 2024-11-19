package org.olhab.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.olhab.springboot.dto.taskhistory.TaskHistoryResponseDto;
import org.olhab.springboot.dto.todo.TodoCreateDto;
import org.olhab.springboot.dto.todo.TodoResponseDto;
import org.olhab.springboot.dto.todo.TodoUpdateDto;
import org.olhab.springboot.service.TaskHistoryService;
import org.olhab.springboot.service.TodoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    private final TaskHistoryService taskHistoryService;

    @PostMapping
    public TodoResponseDto create(@RequestBody final TodoCreateDto todoCreateDto) {
        return todoService.save(todoCreateDto);
    }

    @PutMapping("/{id}")
    public TodoResponseDto update(@PathVariable("id") final Long todoId,
                          @RequestBody TodoUpdateDto todoUpdateDto)
                          throws JsonProcessingException {
        return todoService.update(todoUpdateDto, todoId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long todoId) {
        todoService.deleteById(todoId);
    }

    @GetMapping("/{id}/history")
    public List<TaskHistoryResponseDto> getHistoryForTodo(@PathVariable("id") Long todoId) {
        return taskHistoryService.getAllForTodoId(todoId);
    }
}
