package ru.nextcloudnext.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String patronymic;
}
