package com.spring.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    // sockJS Fallback을 이용해 노출할 endpoint 설정
    //서버와 처음 연결해주는 부분
    //WebSocket Open!
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓이 handshake를 하기 위해 연결하는 endpoint
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        // SockJS는 WebSocket을 지원하지 않는 브라우저에서 HTTP의 Polling과 같은 방식으로 WebSocket의 요청을 수행하도록 도와준다.
        // SockJS를 사용할 경우, 클라이언트에서 WebSocket 요청을 보낼 때 설정한 엔드포인트 뒤에 /webSocket를 추가해줘야 정상 작동된다.
    }

    //메세지 브로커에 관한 설정
    //메세지 송수신을 처리하는 부분
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버 -> 클라이언트로 발행하는 메세지에 대한 endpoint 설정 : 구독
        //sub 로 보내면 이곳을 한번 거쳐서 프론트에 데이터를 전달해준다.
        registry.enableSimpleBroker("/sub");

        // 클라이언트 -> 서버로 발행하는 메세지에 대한 endpoint 설정 : 구독에 대한 메세지
        //pub 로 데이터를 받으면 이곳을 한번 거쳐서 URI 만 MessageMapping 에 매핑이 된다.
        //ex pub/chat/message 라면 pub 를 제외하고 /chat/message 를 @MessageMapping 에 매핑한다.
        registry.setApplicationDestinationPrefixes("/pub");
    }


}
