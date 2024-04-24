package com.PFA2.EduHousing.services.chatRoomService;

import java.util.Optional;

public interface ChatRoomService {
    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    );
    public String createChatId(String senderId, String recipientId);
}
