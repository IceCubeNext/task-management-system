package ru.nextcloudnext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nextcloudnext.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleById(Long id);
}
