package ru.nextcloudnext.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Vladimir F. 27.03.2024
 */
@Entity
@Table(name = "token_sessions")
@Getter
@Setter
@NoArgsConstructor
public class TokenSession extends BaseEntity {

    @Column(name = "session_hash")
    private String sessionHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenSession that = (TokenSession) o;
        return Objects.equals(sessionHash, that.sessionHash) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionHash, user);
    }

    @Override
    public String toString() {
        return "TokenSession{" +
                "sessionHash='" + sessionHash + '\'' +
                ", user=" + user +
                ", id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}

