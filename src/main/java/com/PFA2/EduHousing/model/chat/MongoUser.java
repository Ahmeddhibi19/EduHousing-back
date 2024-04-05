package com.PFA2.EduHousing.model.chat;

import com.PFA2.EduHousing.model.ConnexionStatus;
import com.PFA2.EduHousing.model.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document
@Builder
public class MongoUser {
    @Id
    private String id;
    private String fullName;
    private ConnexionStatus status;
}
