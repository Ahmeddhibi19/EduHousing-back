package com.PFA2.EduHousing.model.chat;

import com.PFA2.EduHousing.model.AbstractEntity;
import com.PFA2.EduHousing.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatRoom {
    @Id
    private Integer id;
    private String chatId;
    private Integer senderId;
    private Integer receiverId;
}
