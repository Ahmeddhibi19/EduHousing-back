package com.PFA2.EduHousing.repository.mongo;

import com.PFA2.EduHousing.model.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mongo_chat")
public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByChatId(String chatId);
}
