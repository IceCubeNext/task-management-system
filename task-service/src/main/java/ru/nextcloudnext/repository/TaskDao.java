package ru.nextcloudnext.repository;

import ru.nextcloudnext.dto.TaskSearchParameters;
import ru.nextcloudnext.model.Task;

import java.util.List;

public interface TaskDao {
    List<Task> findTasks(Integer skip, Integer take, TaskSearchParameters parameters);
}
