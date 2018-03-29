package com.epam.kvk.quiz.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AnswerVotes {

    private String answer;
    private Long votes;

    public AnswerVotes(String answer, Long votes) {
        this.answer = answer;
        this.votes = votes;
    }
}
