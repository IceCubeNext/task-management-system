package ru.nextcloudnext.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Task task;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User author;
}
