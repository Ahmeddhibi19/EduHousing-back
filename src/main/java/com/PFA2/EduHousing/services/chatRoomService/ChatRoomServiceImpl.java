package com.PFA2.EduHousing.services.chatRoomService;

import com.PFA2.EduHousing.model.chat.ChatRoom;
import com.PFA2.EduHousing.repository.mongo.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Optional<String> getChatRoomId(
            Integer senderId,
            Integer recipientId,
            boolean createNewRoomIfNotExists
    ) {
        return chatRoomRepository
                .findChatRoomBySenderIdAndReceiverId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }

                    return  Optional.empty();
                });
    }

    @Override
    public String createChatId(Integer senderId, Integer recipientId) {
        var chatId = String.format("%s_%s", senderId.toString(), recipientId.toString());

        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .receiverId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return chatId;
    }
}
