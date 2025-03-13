package ru.nextcloudnext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nextcloudnext.model.TokenSession;
import ru.nextcloudnext.model.User;

import java.util.Optional;

@Repository
public interface TokenSessionRepository extends JpaRepository<TokenSession, Long> {

    void deleteAllByUser(User user);

    Optional<TokenSession> findBySessionHash(String sessionHash);

    Boolean existsByUser(User user);
}
