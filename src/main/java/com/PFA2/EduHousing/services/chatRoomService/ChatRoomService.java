package com.PFA2.EduHousing.services.chatRoomService;

import java.util.Optional;

public interface ChatRoomService {
    public Optional<String> getChatRoomId(
            Integer senderId,
            Integer recipientId,
            boolean createNewRoomIfNotExists
    );
    public String createChatId(Integer senderId, Integer recipientId);
}
