package com.innocamp.sanmolong.chat.controller;

import com.innocamp.sanmolong.chat.dto.ChatMessage;
import com.innocamp.sanmolong.chat.entity.Chat;
import com.innocamp.sanmolong.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{roomId}/common")
    @SendTo("/room/{roomId}")
    public ChatMessage commonChat(@DestinationVariable Long roomId, ChatMessage message) {
        //채팅 저장
        Chat chat = chatService.createChat(roomId, message.getSender(), message.getMessage());
        return ChatMessage.builder()
                .roomId(roomId)
                .sender(chat.getUser().getNickname())
                .message(chat.getMessage())
                .build();
    }

    @MessageMapping("/{roomId}/emer")
    @SendTo("/room/{roomId}")
    public ChatMessage emergencyChat(@DestinationVariable Long roomId, ChatMessage message) {
        System.out.println("긴급");
        //채팅 저장
        Chat chat = chatService.createChat(roomId, message.getSender(), message.getMessage());
        return ChatMessage.builder()
                .roomId(roomId)
                .sender(chat.getUser().getNickname())
                .message(chat.getMessage())
                .build();
    }

}
