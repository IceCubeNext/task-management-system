package ru.nextcloudnext.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Vladimir F. 14.12.2023
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreationTimestamp
    @Column(name = "created", updatable = false)
    protected LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    protected LocalDateTime updated;
}

