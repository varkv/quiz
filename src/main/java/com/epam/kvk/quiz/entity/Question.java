package com.epam.kvk.quiz.entity;

import com.epam.kvk.quiz.entity.enums.QuestionTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(length = 1024)
    private String name;

    @NonNull
    @Column(length = 4068)
    private String text;

    @NonNull
    @Enumerated(EnumType.STRING)
    private QuestionTypeEnum type;

    @ElementCollection
    private List<String> answers;

    @ElementCollection
    private List<String> correct;

    @Column(length = 4068)
    private String link;

    @OneToMany(mappedBy = "user")
    List<Answer> userAnswers;

    public Question(String name, String text, QuestionTypeEnum typeEnum, ArrayList<String> answers, ArrayList<String> correct, String link) {
        this.name = name;
        this.text = text;
        this.type = typeEnum;
        this.answers = answers;
        this.correct = correct;
        this.link = link;
    }
}
