package com.example.pj2be.config.websocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 웹소켓 통신경로를 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // 발행자(요청자)가 해당경로로 메시지를 주면 가공없이 바로 구독자(다른 클라이언트)들에게 전달
        registry.setApplicationDestinationPrefixes("/app"); // 발행자(요청자)가 해당 경로로 메시지를 주면 구독자(다른 클라이언트)들에게 전달
                                                            // 참고로 /app/.. 경로로 메시지를 보내면 이 메시지는 @MessageMapping 어노테이션이 붙은 곳으로 향한다.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // 커넥션을 맺는 경로 설정.
                .withSockJS(); // 프론트에서 SockJS 같이 사용하는 경우


    }
}

/*

        기본적인 웹소켓 흐름: 사용자가 로그인 하면 세션정보를 받고 웹소켓을 연결만 시켜두고,
        실시간 통신(웹소켓 통신)이 필요한 시점에 이벤트를 발생시켜 서버가 일하게 하고,
        사용자가 로그아웃하면 세션도 끊고 웹소켓 연결도 끊으면 된다.

        registry.addHandler(webSocketHandler, "/"): 두번째 인자 값으로는 엔드포인트 주소가 들어간다.
                                                    엔드포인트주소를 "/socket" 로 설정하면 프론트 쪽에서도 똑같이
                                                    웹 서버랑 통신할 소켓 주소를 "/socket" 로 맞춰 줘야 통신이 된다.
                                                    보통 첫 로그인하고 사용자의 세션 정보가 확보가 될 때 웹 소켓을 서로 연결 시켜주고
                                                    만약 로그인에 성공해 세션정보가 있고 메인홈페이지 ("/")로 이동되면 웹소켓 연결을 할 수 있게 설정 가능하다.

        setAllowedOrigins(): origin의 형태는 프로토콜, 호스트, 포트로 이루어져 있다.
                            현재는 테스트 중이기 때문에 ("*") 으로 모든 origin을 허용 시켜두지만
                            나중에 실제 서비스 할 단계에서는 우리의 홈페이지 주소 https://홈페이지주소.com 으로 입력해야한다.
                            https (프로토콜), 홈페이지주소(호스트), com(포트)

     */
