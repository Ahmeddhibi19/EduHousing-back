package com.PFA2.EduHousing.model.chat;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Chat {
    @Id
    private String id;
    private String chatId;
    private Integer senderId;
    private Integer recipientId;
    private String content;
    private Date timestamp;
}
