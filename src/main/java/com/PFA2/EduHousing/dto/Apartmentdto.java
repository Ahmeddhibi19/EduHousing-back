package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Apartmentdto {

    private Integer id;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String address;

    private String description;

    private Double rating;

    private String type;

    private Boolean isRented;


    @JsonIgnore
    private Homeownerdto homeowner;

    @JsonIgnore
    private List<Distancedto> distances = new ArrayList<>();

    @JsonIgnore
    private Citydto citydto;

    @JsonIgnore
    private List<ApartmentImagedto> images = new ArrayList<>();

    @JsonIgnore
    private Set<Favouritesdto> favourites = new HashSet<>();

    @JsonIgnore
    private List<RentalDetailsdto> rentalDetails = new ArrayList<>();

    @JsonIgnore
    private Set<RentalFeedbackdto> rentalFeedbacks = new HashSet<>();

    public static Apartmentdto fromEntity(Apartment apartment){
        if(apartment==null){
            return null;
        }
        return Apartmentdto.builder()
                .id(apartment.getId())
                .address(apartment.getAddress())
                .description(apartment.getDescription())
                .latitude(apartment.getLatitude())
                .longitude(apartment.getLongitude())
                .type(apartment.getType())
                .rating(apartment.getRating())
                .isRented(apartment.getIsRented())
                .build();
    }

    public static Apartment toEntity(Apartmentdto apartmentdto){
        if (apartmentdto==null){
            return null;
        }
        Apartment apartment =new Apartment();
        apartment.setId(apartmentdto.getId());
        apartment.setAddress(apartmentdto.getAddress());
        apartment.setLatitude(apartmentdto.getLatitude());
        apartment.setLongitude(apartmentdto.getLongitude());
        apartment.setDescription(apartmentdto.getDescription());
        apartment.setRating(apartmentdto.getRating());
        apartment.setType(apartmentdto.getType());
        apartment.setIsRented(apartmentdto.getIsRented());

        return apartment;
    }
}
