package com.epam.kvk.quiz.converter;

import com.epam.kvk.quiz.dto.QuestionDto;
import com.epam.kvk.quiz.entity.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class QuestionConverter {

    public QuestionDto toDto(Question question){
        return new QuestionDto(
                question.getId(),
                question.getName(),
                question.getText(),
                question.getType(),
                question.getAnswers()
        );
    }

    public List<QuestionDto> toDto(List<Question> questions){
        return questions.stream().map(this::toDto).collect(Collectors.toList());
    }
}
