package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.ApplicationFeedback;
import com.PFA2.EduHousing.model.Homeowner;
import com.PFA2.EduHousing.model.Student;
import com.PFA2.EduHousing.model.User;
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


    private Homeownerdto homeowner;


    private Studentdto student;

    public static ApplicationFeedbackdto fromEntity(ApplicationFeedback applicationFeedback){
        if(applicationFeedback==null){
            return null;
        }
        return ApplicationFeedbackdto.builder()
                .id(applicationFeedback.getId())
                .content(applicationFeedback.getContent())
                .rating(applicationFeedback.getRating())
                .student(
                        applicationFeedback.getUser() instanceof Student ?
                                Studentdto.builder()
                                        .id(applicationFeedback.getUser().getId())
                                        .email(applicationFeedback.getUser().getEmail())
                                        .firstName(applicationFeedback.getUser().getFirstName())
                                        .lastName(applicationFeedback.getUser().getLastName())
                                        .phoneNumber(applicationFeedback.getUser().getPhoneNumber())
                                        .build():null
                )
                .homeowner(
                        applicationFeedback.getUser() instanceof Homeowner?
                                Homeownerdto.builder()
                                        .id(applicationFeedback.getUser().getId())
                                        .email(applicationFeedback.getUser().getEmail())
                                        .firstName(applicationFeedback.getUser().getFirstName())
                                        .lastName(applicationFeedback.getUser().getLastName())
                                        .phoneNumber(applicationFeedback.getUser().getPhoneNumber())
                                        .build():null
                )
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

        User user = null;
        if(applicationFeedbackdto.getStudent()!=null){
            user=Studentdto.toEntity(applicationFeedbackdto.getStudent());
        } else if (applicationFeedbackdto.getHomeowner()!=null) {
            user=Homeownerdto.toEntity(applicationFeedbackdto.getHomeowner());

        }

        applicationFeedback.setUser(user);
        return applicationFeedback;
    }
}
