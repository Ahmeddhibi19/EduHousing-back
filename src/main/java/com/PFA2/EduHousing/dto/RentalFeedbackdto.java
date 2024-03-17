package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.RentalFeedback;
import com.PFA2.EduHousing.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RentalFeedbackdto {

    private Integer id;

    private String content;


    private Integer rating;

    @JsonIgnore
    private Apartmentdto apartment;

    @JsonIgnore
    private Studentdto student;

    public static RentalFeedbackdto fromEntity(RentalFeedback rentalFeedback){
        if(rentalFeedback==null){
            return  null;
        }
        return RentalFeedbackdto.builder()
                .id(rentalFeedback.getId())
                .content(rentalFeedback.getContent())
                .rating(rentalFeedback.getRating())
                .build();
    }

    public static RentalFeedback toEntity(RentalFeedbackdto rentalFeedbackdto){
        if(rentalFeedbackdto==null){
            return null;
        }

        RentalFeedback rentalFeedback= new RentalFeedback();
        rentalFeedback.setId(rentalFeedbackdto.getId());
        rentalFeedback.setContent(rentalFeedbackdto.getContent());
        rentalFeedback.setRating(rentalFeedbackdto.getRating());
        return rentalFeedback;
    }
}
