package com.PFA2.EduHousing.repository.mongo;

import com.PFA2.EduHousing.model.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    Optional<ChatRoom> findChatRoomBySenderIdAndReceiverId(String senderId,String receiverId);
}
