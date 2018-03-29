package com.epam.kvk.quiz.service;

import com.epam.kvk.quiz.converter.QuestionConverter;
import com.epam.kvk.quiz.dto.QuestionStatisticDto;
import com.epam.kvk.quiz.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebSocketCommunicationService {

    @Autowired
    private SimpMessagingTemplate webSocket;

    public void setState(Map<String, Object> currentState) {
        webSocket.convertAndSend("/topic/status", currentState);
    }

    public void callAnswer() {
        webSocket.convertAndSend("/topic/answer/call", "{}");
    }
}
