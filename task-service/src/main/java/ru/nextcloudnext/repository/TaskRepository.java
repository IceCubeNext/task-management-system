package ru.nextcloudnext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nextcloudnext.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskDao {
}
