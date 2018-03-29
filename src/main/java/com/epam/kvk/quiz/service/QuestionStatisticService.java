package com.epam.kvk.quiz.service;

import com.epam.kvk.quiz.dto.AnswerVotes;
import com.epam.kvk.quiz.dto.QuestionStatisticDto;
import com.epam.kvk.quiz.entity.Question;
import com.epam.kvk.quiz.repository.AnswerRepository;
import com.epam.kvk.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionStatisticService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    public QuestionStatisticDto getQuestionStatistic(Long questionId) {
        QuestionStatisticDto dto = new QuestionStatisticDto();
        Question question = questionRepository.findOne(questionId);
        List<String> correct = question.getCorrect();
        if(correct != null){
            dto.setCorrect(correct.stream().collect(Collectors.toList()));
        }
        dto.setLink(question.getLink());
        dto.setVotes(question.getAnswers().stream().map(answer ->
            new AnswerVotes(answer, answerRepository.countByAnswerAndQuestion(answer, question))
        ).collect(Collectors.toList()));

        return dto;
    }
}
