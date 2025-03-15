package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.NewTaskDto;
import ru.nextcloudnext.dto.TaskDto;
import ru.nextcloudnext.dto.TaskSearchParameters;
import ru.nextcloudnext.model.Task;

import java.util.List;

public interface TaskService {
    Task findById(Long id);

    List<TaskDto> getTasks(Integer skip, Integer take, TaskSearchParameters parameters);

    TaskDto getTaskById(Long id);

    TaskDto addTask(NewTaskDto taskDto);

    TaskDto updateTask(Long id, NewTaskDto taskDto);

    void deleteTask(Long id);
}
