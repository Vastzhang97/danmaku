package com.niit.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageWebSocket(), "/MessageWebSocket").setAllowedOrigins("*").addInterceptors(new WebSocketInterceptor());//注册WebSocket拦截器
        registry.addHandler(videoWebSocket(), "/VideoWebSocket").setAllowedOrigins("*").addInterceptors(new WebSocketInterceptor());//注册WebSocket拦截器
    }

    @Bean
    public WebSocketHandler messageWebSocket() {
        return new MessageWebSocket();
    }

    @Bean
    public WebSocketHandler videoWebSocket() {
        return new VideoWebSocket();
    }

}
