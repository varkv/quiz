package com.epam.kvk.quiz.controller;

import com.epam.kvk.quiz.dto.ClientAnswerDto;
import com.epam.kvk.quiz.dto.QuestionStatisticDto;
import com.epam.kvk.quiz.service.QuizContextHolder;
import com.epam.kvk.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class QuestionController {

    @Autowired
    QuizService quizService;

    @Autowired
    QuizContextHolder quizContextHolder;

    @MessageMapping("/stomp/status")
    @SendTo("/topic/status")
    public Map<String, Object> status() {
        return quizContextHolder.getCurrentState();
    }

    @MessageMapping("/stomp/answer/save")
    @SendTo("/topic/answer/response")
    public boolean saveResult(Principal principal, ClientAnswerDto clientAnswerDto) {
        quizService.saveAnswer(principal.getName(), clientAnswerDto);
        return true;
    }
}
