package com.innocamp.sanmolong.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/send"); // 바로 브로커로 가는 것이 아니라 메시지의 처리나 가공이 필요 시 Handler로 전달
        registry.enableSimpleBroker("/room"); // enableSimpleBroker = spring 내장 broker, /room이 prefix로 붙은 메시지가 송신되었을 때 Broker가 구독자들에게 전달
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-stomp")
                .withSockJS();
    }
}
