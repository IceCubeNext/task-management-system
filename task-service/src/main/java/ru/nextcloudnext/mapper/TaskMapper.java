package ru.nextcloudnext.mapper;

import org.mapstruct.Mapper;
import ru.nextcloudnext.dto.NewTaskDto;
import ru.nextcloudnext.dto.TaskDto;
import ru.nextcloudnext.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);

    Task toModel(NewTaskDto taskDto);
}
