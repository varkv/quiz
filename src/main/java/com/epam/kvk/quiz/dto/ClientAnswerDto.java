package com.epam.kvk.quiz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ClientAnswerDto {
    private Long questionId;
    private String answer;
}
