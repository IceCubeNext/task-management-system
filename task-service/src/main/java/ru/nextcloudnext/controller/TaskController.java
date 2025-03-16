package ru.nextcloudnext.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Задачи", description = "Работа с задачами")
public class TaskController {
    private final TaskService taskService;

    @Operation(
            summary = "Получение списка задач",
            description = "Получение списка всех задач"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public List<TaskDto> getTasks(
            @Parameter(description = "Идентификатор автора задачи", example = "1")
            @RequestParam(required = false) Long authorId,
            @Parameter(description = "Идентификатор исполнителя задачи", example = "1")
            @RequestParam(required = false) Long performerId,
            @Parameter(description = "Текст из заголовка или описания в любом регистре", example = "Задача 1")
            @RequestParam(required = false) String searchValue,
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(required = false) Integer skip,
            @Parameter(description = "Количество элементов на странице", example = "10")
            @RequestParam(required = false) Integer take) {
        TaskSearchParameters parameters = new TaskSearchParameters(authorId, performerId, searchValue);
        log.info("Get tasks from={} size={} authorId={} performerId={} text={}",
                skip, take, authorId, performerId, searchValue);
        return taskService.getTasks(skip, take, parameters);
    }

    @Operation(
            summary = "Получение задачи по ее id",
            description = "Получение задачи по ее id"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public TaskDto getTaskById(
            @Parameter(description = "Идентификатор задачи", required = true, example = "1")
            @PathVariable Long id) {
        log.info("Get task with id={}", id);
        return taskService.getTaskById(id);
    }

    @Operation(
            summary = "Добавление задачи",
            description = "Добавление новой задачи"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TaskDto addTask(@RequestBody @Validated({OnCreate.class}) NewTaskDto taskDto) {
        log.info("Post task={}", taskDto);
        return taskService.addTask(taskDto);
    }

    @Operation(
            summary = "Обновление задачи",
            description = "Обновление задачи. Для обновления необходимо быть автором задачи либо администратором"
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}")
    public TaskDto updateTask(
            @Parameter(description = "Идентификатор задачи", required = true, example = "1")
            @PathVariable Long id, @RequestBody @Validated({OnUpdate.class}) NewTaskDto taskDto) {
        log.info("Patch task with id={}, task={}", id, taskDto);
        return taskService.updateTask(id, taskDto);
    }

    @Operation(
            summary = "Удаление задачи",
            description = "Удаление задачи. Для удаления необходимо быть автором задачи либо администратором"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteTask(
            @Parameter(description = "Идентификатор задачи", required = true, example = "1")
            @PathVariable Long id) {
        log.info("Delete task with id={}", id);
        taskService.deleteTask(id);
    }
}
