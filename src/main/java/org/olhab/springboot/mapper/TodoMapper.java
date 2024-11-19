package org.olhab.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.olhab.springboot.config.MapperConfig;
import org.olhab.springboot.dto.todo.TodoCreateDto;
import org.olhab.springboot.dto.todo.TodoResponseDto;
import org.olhab.springboot.dto.todo.TodoUpdateDto;
import org.olhab.springboot.model.Todo;

import java.util.Optional;

@Mapper(config = MapperConfig.class)
public interface TodoMapper {
    TodoResponseDto toDto(Todo todo);

    Todo toModelFromCreateDto(TodoCreateDto createDto);

    Todo toModelFromUpdateDto(TodoUpdateDto updateDto);

    @Named("todoById")
    default Todo todoById(Long id) {
        return Optional.ofNullable(id)
                .map(Todo::new)
                .orElse(null);
    }
}
