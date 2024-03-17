package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.ApplicationFeedback;
import com.PFA2.EduHousing.model.Homeowner;
import com.PFA2.EduHousing.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationFeedbackdto {

    private Integer id;


    private String content;

    private Integer rating;

    @JsonIgnore
    private Homeownerdto homeowner;

    @JsonIgnore
    private Studentdto student;

    public static ApplicationFeedbackdto fromEntity(ApplicationFeedback applicationFeedback){
        if(applicationFeedback==null){
            return null;
        }
        return ApplicationFeedbackdto.builder()
                .id(applicationFeedback.getId())
                .content(applicationFeedback.getContent())
                .rating(applicationFeedback.getRating())
                .build();
    }
    public static ApplicationFeedback toEntity(ApplicationFeedbackdto applicationFeedbackdto){
        if(applicationFeedbackdto==null){
            return null;
        }
        ApplicationFeedback applicationFeedback= new ApplicationFeedback();
        applicationFeedback.setId(applicationFeedbackdto.getId());
        applicationFeedback.setContent(applicationFeedbackdto.getContent());
        applicationFeedback.setRating(applicationFeedbackdto.getRating());
        return applicationFeedback;
    }
}
