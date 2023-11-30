package com.example.pj2be.controller.websocketcontroller;

import com.example.pj2be.service.websocketservice.WebSocketService;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;

    @MessageMapping("/main")
    public void sendMessage(String message, Session session) {
        webSocketService.onMessage(message, session);
    }
}
