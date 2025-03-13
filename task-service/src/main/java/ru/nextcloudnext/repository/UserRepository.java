package ru.nextcloudnext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nextcloudnext.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
