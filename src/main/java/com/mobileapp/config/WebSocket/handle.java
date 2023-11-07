package com.mobileapp.config.WebSocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class handle {
    private final SimpMessageSendingOperations messageSending;
    public void handleWebSocketSend(String userId, String message){
            messageSending.convertAndSendToUser(userId,"/topic/public",message);
        }
}
