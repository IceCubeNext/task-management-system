package ru.nextcloudnext.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nextcloudnext.dto.NewTaskDto;
import ru.nextcloudnext.dto.TaskDto;
import ru.nextcloudnext.dto.TaskSearchParameters;
import ru.nextcloudnext.model.Marker.*;
import ru.nextcloudnext.service.TaskService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks(@RequestParam(required = false) Long authorId,
                                  @RequestParam(required = false) Long performerId,
                                  @RequestParam(required = false) String searchValue,
                                  @RequestParam(defaultValue = "0") Integer skip,
                                  @RequestParam(defaultValue = "10") Integer take) {
        TaskSearchParameters parameters = new TaskSearchParameters(authorId, performerId, searchValue);
        log.info("Get tasks from={} size={} authorId={} performerId={} text={}",
                skip, take, authorId, performerId, searchValue);
        return taskService.getTasks(skip, take, parameters);
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        log.info("Get task with id={}", id);
        return taskService.getTaskById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TaskDto addTask(@RequestBody @Validated({OnCreate.class}) NewTaskDto taskDto) {
        log.info("Post task={}", taskDto);
        return taskService.addTask(taskDto);
    }

    @PatchMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody @Validated({OnUpdate.class}) NewTaskDto taskDto) {
        log.info("Patch task with id={}, task={}", id, taskDto);
        return taskService.updateTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        log.info("Delete task with id={}", id);
        taskService.deleteTask(id);
    }
}
