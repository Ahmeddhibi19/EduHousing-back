package com.PFA2.EduHousing.services.chatService;

import com.PFA2.EduHousing.model.chat.Chat;

import java.util.List;

public interface ChatService {
    public Chat save(Chat chatMessage);
    public List<Chat> findChatMessages(Integer senderId, Integer recipientId);
}
