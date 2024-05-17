package com.spring.portfolio.config;

//import com.spring.portfolio.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private String port;

//    @Value("${spring.data.redis.password}")
//    private String password;

    // redis 연결을 위해 RedisConnectionFactory 사용
    // RedisStandaloneConfiguration 객체를 통해 hostName, Port, Password 정보 입력
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(Integer.parseInt(port));
//        redisStandaloneConfiguration.setPassword(password);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        return lettuceConnectionFactory;
    }

    // <String, Object> 였던걸 바꿈
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

//    // ChannelTopic 등록
//    @Bean
//    public ChannelTopic channelTopic() {
//        return new ChannelTopic("chatroom");
//    }
//
//
//    // MessageListenerAdapter 등록
//    @Bean
//    public MessageListenerAdapter listenerAdapter(RedisSubscriber redisSubscriber) {
//        return new MessageListenerAdapter(redisSubscriber, "sendMessage");
//
//    }
//
//    // 등록한 ChannelTopic, MessageListenerAdapter 로 RedisMessageListenerContainer 등록
//    // redis pub/sub 메시지를 처리하는 listener 설정
//    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
//                                                                       MessageListenerAdapter listenerAdapter,
//                                                                       ChannelTopic channelTopic) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapter, channelTopic);
//        return container;
//    }
//
////    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
////        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
////        container.setConnectionFactory(connectionFactory);
////        return container;
////    }

}
