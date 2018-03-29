package com.epam.kvk.quiz.service;

import com.epam.kvk.quiz.dto.ClientAnswerDto;
import com.epam.kvk.quiz.dto.QuestionStatisticDto;
import com.epam.kvk.quiz.entity.Question;
import com.epam.kvk.quiz.exception.QuizNotStartedException;
import com.epam.kvk.quiz.repository.AnswerRepository;
import com.epam.kvk.quiz.repository.QuestionRepository;
import com.epam.kvk.quiz.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class QuizService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WebSocketCommunicationService webSocketCommunicationService;

    @Autowired
    QuestionContextHolder questionContextHolder;

    @Autowired
    QuizContextHolder quizContextHolder;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    public void start() {
        quizContextHolder.commitAction(QuizContextHolder.ActionEnum.START);
        Question question = questionContextHolder.getNextQuestion();
        webSocketCommunicationService.setState(quizContextHolder.getCurrentState());
    }

    public void next() {
        quizContextHolder.commitAction(QuizContextHolder.ActionEnum.FORWARD);
        Question question = questionContextHolder.getNextQuestion();
        webSocketCommunicationService.setState(quizContextHolder.getCurrentState());
    }

    public void prev() {
        quizContextHolder.commitAction(QuizContextHolder.ActionEnum.PREVIOUS);
        Question question = questionContextHolder.getPrevQuestion();
        webSocketCommunicationService.setState(quizContextHolder.getCurrentState());
    }

    public void disable() {
        quizContextHolder.commitAction(QuizContextHolder.ActionEnum.DISABLE);
        webSocketCommunicationService.setState(quizContextHolder.getCurrentState());
        webSocketCommunicationService.callAnswer();
        setDelayedDisabledStateWithStatistic();
    }

    @Async
    void setDelayedDisabledStateWithStatistic() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                webSocketCommunicationService.setState(quizContextHolder.getCurrentState());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void finish() {
        quizContextHolder.commitAction(QuizContextHolder.ActionEnum.FINISH);
        webSocketCommunicationService.setState(quizContextHolder.getCurrentState());
    }

    @Transactional
    public QuestionStatisticDto statistic() {
        QuestionStatisticDto statistic = questionContextHolder.getStatic();
        return statistic;
    }

    public Long getTotalQuestionCount() {
        return questionContextHolder.getTotalQuestionCount();
    }

    public void saveAnswer(String name, ClientAnswerDto clientAnswerDto) {
        log.debug(String.format("Save %s correct on %s - %s", name, clientAnswerDto.getQuestionId(), clientAnswerDto.getAnswer()));
        if(answerRepository.countByUser_NameAndQuestion_id(name, clientAnswerDto.getQuestionId()) > 0){
            log.debug(String.format("ERROR duplicate - %s correct on %s - %s", name, clientAnswerDto.getQuestionId(), clientAnswerDto.getAnswer()));
        }else {
            answerRepository.createAnswer(userRepository.findByName(name),
                    questionRepository.findOne(clientAnswerDto.getQuestionId()),
                    clientAnswerDto.getAnswer());
        }
    }
}
