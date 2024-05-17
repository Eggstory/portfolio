//package com.spring.portfolio.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@EnableWebSocket
//@RequiredArgsConstructor
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    // 이 핸들러가 웹소켓 통신을 처리해준다
//    private final WebSocketHandler webSocketHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // endpoint 설정 : /api/v1/chat/{postId}
//        // 이를 통해서 ws://localhost:8090/ws/chat 으로 요청이 들어오면 websocket 통신을 진행한다.
//        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌 == 모든 cors 요청을 허용
//        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
//    }
//}
