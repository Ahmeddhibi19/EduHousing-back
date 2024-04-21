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
import java.util.stream.Collectors;

@Data
@Builder
public class RentalDetailsdto {

    private Integer id;

    private Double monthlyAmount;


    private Date startDate;


    private Date endDate;


    private String description;

    private Boolean isCurrent;


    private Set<Requestdto> requestdtoSet= new HashSet<>();


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
                .requestdtoSet(
                        rentalDetails.getRequestSet()!=null?
                                rentalDetails.getRequestSet().stream()
                                        .map(Requestdto::fromEntity).collect(Collectors.toSet())
                                        :null
                )
                .apartment(
                        Apartmentdto.builder()
                                .id(rentalDetails.getApartment().getId())
                                .address(rentalDetails.getApartment().getAddress())
                                .type(rentalDetails.getApartment().getType())
                                .images(
                                        rentalDetails.getApartment().getImageList().stream().map(ApartmentImagedto::fromEntity)
                                                .collect(Collectors.toList())
                                )
                                .rating(rentalDetails.getApartment().getRating())
                                .isRented(rentalDetails.getApartment().getIsRented())
                                .homeowner(
                                        Homeownerdto.builder()
                                                .id(rentalDetails.getApartment().getHomeowner().getId())
                                                .email(rentalDetails.getApartment().getHomeowner().getEmail())
                                                .firstName(rentalDetails.getApartment().getHomeowner().getFirstName())
                                                .lastName(rentalDetails.getApartment().getHomeowner().getLastName())
                                                .phoneNumber(rentalDetails.getApartment().getHomeowner().getPhoneNumber())
                                        .build()
                                )
                                .citydto(
                                        Citydto.builder()
                                                .name(rentalDetails.getApartment().getCity().getName())
                                                .build()
                                )
                                .build()
                )

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
        rentalDetails.setApartment(Apartmentdto.toEntity(rentalDetailsdto.getApartment()));
        return rentalDetails;
    }
}
