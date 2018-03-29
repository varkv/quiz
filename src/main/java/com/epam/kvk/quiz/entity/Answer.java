package com.epam.kvk.quiz.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Question question;

    private String answer;

    @ManyToOne
    private User user;
}
