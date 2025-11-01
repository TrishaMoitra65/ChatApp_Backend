package com.example.chatApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Frontend connects to this endpoint
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // allow React or frontend to connect
                .withSockJS(); // fallback for browsers that don’t support WebSocket
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // For client subscriptions (topics)
        registry.enableSimpleBroker("/topic");
        // For sending messages from client
        registry.setApplicationDestinationPrefixes("/app");
    }
}
