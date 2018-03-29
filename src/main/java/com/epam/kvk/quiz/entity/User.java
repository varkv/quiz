package com.epam.kvk.quiz.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UserRole> roles;

    @OneToMany(mappedBy = "user")
    List<Answer> answers;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, ArrayList<UserRole> userRoles) {
        this.name = name;
        this.password = password;
        this.roles = userRoles;
    }
}
