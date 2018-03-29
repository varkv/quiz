package com.epam.kvk.quiz.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class QuestionStatisticDto {

    private List<String> correct;
    private String link;
    private List<AnswerVotes> votes;
}
