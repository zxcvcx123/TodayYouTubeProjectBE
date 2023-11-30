package com.example.pj2be.service.websocketservice;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Service
@ServerEndpoint("/socket/chatt")
public class WebSocketService {

    private static Set<Session> clients =
            Collections.synchronizedSet(new HashSet<Session>());
    private static Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    @OnOpen
    public void onOpen(Session session) throws Exception{
        logger.info("open session : {}, clients={}",
                session.toString(),
                clients);

        if(!clients.contains(session)){
            clients.add(session);
            logger.info("seesion open : {}", session);
        } else {
            logger.info("이미 연결된 session 입니다.");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session){
        logger.info("receive message : {}", message);

        for(Session s : clients){
            logger.info("send data : {}", message);
            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session){
        logger.info("session close : {}", session);
        clients.remove(session);
    }
}
