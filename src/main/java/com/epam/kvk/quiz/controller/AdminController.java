package com.epam.kvk.quiz.controller;

import com.epam.kvk.quiz.exception.QuizException;
import com.epam.kvk.quiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuizService quizService;

    @RequestMapping("/start")
    public void start() {
        quizService.start();
    }

    @RequestMapping("/next")
    public void next() {
        quizService.next();
    }

    @RequestMapping("/prev")
    public void prev() {
        quizService.prev();
    }

    @RequestMapping("/disable")
    public void disable() {
        quizService.disable();
    }

    @RequestMapping("/finish")
    public void finish() {
        quizService.finish();
    }

}
