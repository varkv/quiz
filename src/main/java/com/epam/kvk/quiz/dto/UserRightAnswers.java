package com.epam.kvk.quiz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserRightAnswers implements Comparable<UserRightAnswers>{
    private String userName;
    private Long rightAnswers;

    public UserRightAnswers(String userName, Long rightAnswers) {
        this.userName = userName;
        this.rightAnswers = rightAnswers;
    }

    @Override
    public int compareTo(UserRightAnswers o) {
        return rightAnswers.compareTo(o.getRightAnswers());
    }
}
