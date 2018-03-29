package com.epam.kvk.quiz.service;

import com.epam.kvk.quiz.converter.QuestionConverter;
import com.epam.kvk.quiz.exception.UnsupportedActionException;
import com.epam.kvk.quiz.exception.UnsupportedStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizContextHolder {

    private StateEnum currentState;

    @Autowired
    QuestionContextHolder questionContextHolder;

    @Autowired
    QuestionConverter questionConverter;

    @PostConstruct
    void init() {
        setState(StateEnum.BEGIN);
    }

    private synchronized void setState(StateEnum state) {
        currentState = state;
    }

    public void commitAction(ActionEnum action) {
        if (Arrays.stream(currentState.getAvailableActions()).anyMatch(
                availableAction -> availableAction.equals(action))) {
            setState(action.getNextState());
        } else {
            throw new UnsupportedActionException();
        }
    }

    public enum StateEnum {
        BEGIN,
        QUESTION,
        DISABLED,
        END;

        ActionEnum[] getAvailableActions() {
            switch (this) {
                case BEGIN:
                    return new ActionEnum[]{ActionEnum.START};
                case QUESTION:
                    return new ActionEnum[]{ActionEnum.DISABLE};
                case DISABLED:
                    return new ActionEnum[]{ActionEnum.PREVIOUS, ActionEnum.FORWARD, ActionEnum.FINISH};
                case END:
                    return new ActionEnum[]{};
            }
            throw new UnsupportedStateException();
        }
    }

    public enum ActionEnum {
        START,
        PREVIOUS,
        DISABLE,
        FORWARD,
        FINISH;

        StateEnum getNextState() {
            switch (this) {
                case START:
                    return StateEnum.QUESTION;
                case PREVIOUS:
                    return StateEnum.QUESTION;
                case FORWARD:
                    return StateEnum.QUESTION;
                case DISABLE:
                    return StateEnum.DISABLED;
                case FINISH:
                    return StateEnum.END;
            }
            throw new UnsupportedActionException();
        }
    }

    public Map<String, Object> getCurrentState() {
        Map<String, Object> map = new HashMap<>();
        map.put("state", currentState.name());
        map.put("actions", Arrays.stream(currentState.getAvailableActions()).map(Enum::name).collect(Collectors.toList()));
        if(currentState.equals(StateEnum.QUESTION)
                || currentState.equals(StateEnum.DISABLED)) {
            map.put("question", questionConverter.toDto(questionContextHolder.getCurrentQuestion()));
        }else{
            map.put("question", null);
        }
        map.put("total_question_number", questionContextHolder.getTotalQuestionCount());
        map.put("current_question_number", questionContextHolder.getCurrentQuestionNumber());

        if(currentState.equals(StateEnum.DISABLED)){
            map.put("question_statistic", questionContextHolder.getStatic());
        }else{
            map.put("question_statistic", null);
        }
        if(currentState.equals(StateEnum.END)){
            map.put("user_statistic", questionContextHolder.getUserRightAnswers());
        }else{
            map.put("user_statistic", null);
        }
        map.put("user_statistic", questionContextHolder.getUserRightAnswers());
        return map;
    }
}