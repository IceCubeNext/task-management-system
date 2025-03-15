package ru.nextcloudnext.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nextcloudnext.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskId(Long taskId);

    Page<Comment> findAllByTaskId(Long taskId, Pageable page);

    List<Comment> findAllByTaskIdIn(List<Long> ids);
}
