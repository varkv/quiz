package com.epam.kvk.quiz.dto;

import com.epam.kvk.quiz.entity.enums.QuestionTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class QuestionDto {
    private Long id;
    private String name;
    private String text;
    private String type;
    private List<String> answers;

    public QuestionDto(Long id, String name, String text, QuestionTypeEnum type, List<String> answers) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.type = type.name();
        this.answers = answers;
    }
}
