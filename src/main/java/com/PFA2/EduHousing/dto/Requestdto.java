package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.Utils.ImageUtils;
import com.PFA2.EduHousing.model.RentalDetails;
import com.PFA2.EduHousing.model.Request;
import com.PFA2.EduHousing.model.Status;
import com.PFA2.EduHousing.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.stream.Collectors;

@Data
@Builder
public class Requestdto {

    private Integer id;

    private String content;

    private Status status;

    private Instant acceptanceTime;

    private Instant validationTime;

    private Instant acceptanceDelay ;

    private Instant validationDelay ;


    private RentalDetailsdto rentalDetails;


    private Studentdto student;


    public static Requestdto fromEntity(Request request){
        if (request==null){
            return null;
        }
        return Requestdto.builder()
                .id(request.getId())
                .content(request.getContent())
                .status(request.getStatus())
                .acceptanceTime(request.getAcceptanceTime())
                .validationTime(request.getValidationTime())
                .acceptanceDelay(request.getAcceptanceDelay())
                .validationDelay(request.getValidationDelay())
                .rentalDetails(
                        RentalDetailsdto.builder()
                                .id(request.getRentalDetails().getId())
                                .startDate(request.getRentalDetails().getStartDate())
                                .endDate(request.getRentalDetails().getEndDate())
                                .description(request.getRentalDetails().getDescription())
                                .isCurrent(request.getRentalDetails().getIsCurrent())
                                .monthlyAmount(request.getRentalDetails().getMonthlyAmount())
                                .apartment(
                                        Apartmentdto.builder()
                                                .id(request.getRentalDetails().getApartment().getId())
                                                .address(request.getRentalDetails().getApartment().getAddress())
                                                .type(request.getRentalDetails().getApartment().getType())
                                                .rating(request.getRentalDetails().getApartment().getRating())
                                                .isRented(request.getRentalDetails().getApartment().getIsRented())
                                                .build()
                                )
                                .build()
                )
                .student(
                        Studentdto.builder()
                                .id(request.getStudent().getId())
                                .email(request.getStudent().getEmail())
                                .firstName(request.getStudent().getFirstName())
                                .lastName(request.getStudent().getLastName())

                                .build()
                )
                .build();
    }


    public static Request toEntity(Requestdto requestdto){
        if(requestdto==null){
            return null;
        }
        Request request=new Request();
        request.setId(requestdto.getId());
        request.setContent(requestdto.getContent());
        request.setStatus(requestdto.getStatus());
        request.setAcceptanceTime(requestdto.getAcceptanceTime());
        request.setValidationTime(requestdto.getValidationTime());
        request.setAcceptanceDelay(requestdto.getAcceptanceDelay());
        request.setValidationDelay(requestdto.getValidationDelay());
        /*request.setStudent(Studentdto.toEntity(requestdto.getStudent()));
        request.setRentalDetails(RentalDetailsdto.toEntity(requestdto.getRentalDetails()));*/
        return request;
    }
}
