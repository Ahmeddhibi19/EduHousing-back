package com.PFA2.EduHousing.services.chatService;

import com.PFA2.EduHousing.model.chat.Chat;
import com.PFA2.EduHousing.repository.mongo.ChatRepository;
import com.PFA2.EduHousing.services.chatRoomService.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{
    private final ChatRepository repository;
    private final ChatRoomService chatRoomService;
    @Autowired
    public ChatServiceImpl(
            ChatRepository repository,
            ChatRoomService chatRoomService
    ){
        this.chatRoomService=chatRoomService;
        this.repository=repository;
    }

    @Override
    public Chat save(Chat chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(); // You can create your own dedicated exception
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public List<Chat> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }
}
