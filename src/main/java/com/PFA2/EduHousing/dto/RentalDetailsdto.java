package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.RentalDetails;
import com.PFA2.EduHousing.model.Request;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class RentalDetailsdto {

    private Integer id;

    private Double monthlyAmount;


    private Date startDate;


    private Date endDate;


    private String description;

    private Boolean isCurrent;

    @JsonIgnore
    private Set<Requestdto> requests= new HashSet<>();

    @JsonIgnore
    private Apartmentdto apartment;

    public static RentalDetailsdto fromEntity(RentalDetails rentalDetails){
        if(rentalDetails==null){
            return null;
        }
        return RentalDetailsdto.builder()
                .id(rentalDetails.getId())
                .monthlyAmount(rentalDetails.getMonthlyAmount())
                .startDate(rentalDetails.getStartDate())
                .endDate(rentalDetails.getEndDate())
                .description(rentalDetails.getDescription())
                .isCurrent(rentalDetails.getIsCurrent())
                .build() ;
    }

    public static RentalDetails toEntity(RentalDetailsdto rentalDetailsdto){
        if(rentalDetailsdto==null){
            return null;
        }
        RentalDetails rentalDetails = new RentalDetails();
        rentalDetails.setId(rentalDetailsdto.getId());
        rentalDetails.setMonthlyAmount(rentalDetailsdto.getMonthlyAmount());
        rentalDetails.setStartDate(rentalDetailsdto.getStartDate());
        rentalDetails.setEndDate(rentalDetailsdto.getEndDate());
        rentalDetails.setDescription(rentalDetailsdto.getDescription());
        rentalDetails.setIsCurrent(rentalDetailsdto.getIsCurrent());
        return rentalDetails;
    }
}
