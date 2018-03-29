package com.epam.kvk.quiz.service;

import com.epam.kvk.quiz.dto.QuestionStatisticDto;
import com.epam.kvk.quiz.dto.UserRightAnswers;
import com.epam.kvk.quiz.entity.Question;
import com.epam.kvk.quiz.exception.QuestionNotFoundException;
import com.epam.kvk.quiz.repository.AnswerRepository;
import com.epam.kvk.quiz.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class QuestionContextHolder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private AtomicLong currentQuestionId = new AtomicLong();
    private AtomicLong currentQuestionNumber = new AtomicLong();
    private Long totalQuestionCount = null;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionStatisticService questionStatisticService;

    @Autowired
    AnswerRepository answerRepository;

    @PostConstruct
    private void init() {
        currentQuestionId.set(-1);
        currentQuestionNumber.set(0);
    }

    public Question getNextQuestion() {
        log.debug(String.format("Current question id %s", currentQuestionId.get()));
        log.debug(String.format("Total count question is %s", questionRepository.count()));
        Question question = questionRepository.findNext(currentQuestionId.get());
        if (question != null) {
            currentQuestionId.set(question.getId());
            currentQuestionNumber.getAndIncrement();
            log.debug(String.format("New current question id %s", currentQuestionId.get()));
            return question;
        } else {
            log.error(String.format("Next question after id %s is not exist!!!", currentQuestionId.get()));
            throw new QuestionNotFoundException();
        }
    }

    public Question getPrevQuestion() {
        log.debug(String.format("Current question id %s", currentQuestionId.get()));
        Question question = questionRepository.findPrevious(currentQuestionId.get());
        if (question != null) {
            currentQuestionId.set(question.getId());
            currentQuestionNumber.getAndDecrement();
            log.debug(String.format("New current question id %s", currentQuestionId.get()));
            return question;
        } else {
            log.error(String.format("Next question before id %s is not exist!!!", currentQuestionId.get()));
            throw new QuestionNotFoundException();
        }
    }

    public QuestionStatisticDto getStatic() {
        log.debug(String.format("Get question statistic by id %s", currentQuestionId.get()));
        return questionStatisticService.getQuestionStatistic(currentQuestionId.get());
    }

    public Long getTotalQuestionCount() {
        if(totalQuestionCount == null){
            totalQuestionCount = questionRepository.count();
        }
        return totalQuestionCount;
    }

    public Long getCurrentQuestionNumber() {
        return currentQuestionNumber.get();
    }

    public Question getCurrentQuestion() {
        log.debug(String.format("Current question id %s", currentQuestionId.get()));
        Question question = questionRepository.findOne(currentQuestionId.get());
        if (question != null) {
            return question;
        } else {
            log.error(String.format("Current question %s is not exist!!!", currentQuestionId.get()));
            throw new QuestionNotFoundException();
        }
    }

    public TreeSet<UserRightAnswers> getUserRightAnswers() {
        return answerRepository.getUserRightAnswers();
    }
}
