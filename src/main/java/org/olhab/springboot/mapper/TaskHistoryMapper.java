package org.olhab.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.olhab.springboot.config.MapperConfig;
import org.olhab.springboot.dto.taskhistory.TaskHistoryCreateDto;
import org.olhab.springboot.dto.taskhistory.TaskHistoryResponseDto;
import org.olhab.springboot.model.TaskHistory;

@Mapper(config = MapperConfig.class, uses = TodoMapper.class)
public interface TaskHistoryMapper {
    @Mapping(target = "todoId", source = "todo.id")
    TaskHistoryResponseDto toDto(TaskHistory taskHistory);

    @Mapping(target = "todo", source = "todoId", qualifiedByName = "todoById")
    TaskHistory toModel(TaskHistoryResponseDto responseDto);

    @Mapping(target = "todo", source = "todoId", qualifiedByName = "todoById")
    TaskHistory toModelFromCreateDto(TaskHistoryCreateDto taskHistoryCreateDto);
}
