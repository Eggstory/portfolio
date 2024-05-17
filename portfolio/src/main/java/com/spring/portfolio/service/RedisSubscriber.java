//package com.spring.portfolio.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.spring.portfolio.entity.ChatMessage;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RedisSubscriber implements MessageListener {
//
//    private static final Logger log = LoggerFactory.getLogger(RedisSubscriber.class);
//    private final RedisTemplate redisTemplate;
//    private final ObjectMapper objectMapper;
//    private final SimpMessageSendingOperations messagingTemplate;
//
////    public void sendMessage(String message) {
////        try {
////            ChatMessage publishMessage = objectMapper.readValue(message, ChatMessage.class);
////            messagingTemplate.convertAndSend("/sub/chat/" + publishMessage.getRoomId(), publishMessage);
////        } catch (Exception e) {
////            log.error(e.getMessage());
////        }
////    }
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        try {
//            // redis에서 발행된 데이터를 받아 deserialize
//            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
//            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
//            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
//}
