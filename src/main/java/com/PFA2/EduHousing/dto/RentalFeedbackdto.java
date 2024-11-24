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


    private Apartmentdto apartment;


    private Studentdto student;

    public static RentalFeedbackdto fromEntity(RentalFeedback rentalFeedback){
        if(rentalFeedback==null){
            return  null;
        }
        return RentalFeedbackdto.builder()
                .id(rentalFeedback.getId())
                .content(rentalFeedback.getContent())
                .rating(rentalFeedback.getRating())
                .apartment(
                        Apartmentdto.builder()
                                .id(rentalFeedback.getApartment().getId())
                                .type(rentalFeedback.getApartment().getType())
                                .address(rentalFeedback.getApartment().getAddress())
                                .build()
                )
                .student(
                        Studentdto.builder()
                                .id(rentalFeedback.getStudent().getId())
                                .email(rentalFeedback.getStudent().getEmail())
                                .firstName(rentalFeedback.getStudent().getFirstName())
                                .lastName(rentalFeedback.getStudent().getLastName())
                                .college(
                                        Collegedto.builder()
                                                .id(rentalFeedback.getStudent().getCollege().getId())
                                                .name(rentalFeedback.getStudent().getCollege().getName())
                                                .address(rentalFeedback.getStudent().getCollege().getAddress())
                                                .build()
                                )
                                .build()
                )
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
        /*rentalFeedback.setStudent(Studentdto.toEntity(rentalFeedbackdto.getStudent()));
        rentalFeedback.setApartment(Apartmentdto.toEntity(rentalFeedbackdto.getApartment()));*/
        return rentalFeedback;
    }
}
