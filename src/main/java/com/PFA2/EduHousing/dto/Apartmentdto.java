package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.Utils.ImageUtils;
import com.PFA2.EduHousing.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    private Map<Integer, BigDecimal> distancesMap;

    private List<byte[]> imageList;

    private Homeownerdto homeowner;

    private List<Distancedto> distances = new ArrayList<>();

    private Citydto citydto;

    private List<ApartmentImagedto> images = new ArrayList<>();

    private Set<Favouritesdto> favourites = new HashSet<>();

    private List<RentalDetailsdto> rentalDetails = new ArrayList<>();

    private Set<RentalFeedbackdto> rentalFeedbacks = new HashSet<>();

    public static Apartmentdto fromEntity(Apartment apartment){
        if(apartment==null){
            return null;
        }
        List<byte[]> imageList=new ArrayList<>();
        if(apartment.getImageList()!=null){
            for (ApartmentImage apartmentImage: apartment.getImageList()){
                imageList.add(ImageUtils.decompressImage(apartmentImage.getData()));
            }
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
                .imageList(imageList)
                .distancesMap(
                        apartment.getDistanceList().stream()
                                .collect(Collectors.toMap(
                                        distancedto -> distancedto.getCollege().getId(),
                                        Distance::getDistanceValue
                                )))
                .homeowner(
                        apartment.getHomeowner()!=null?
                        Homeownerdto.builder()
                                .id(apartment.getHomeowner().getId())
                                .address(apartment.getHomeowner().getAddress())
                                .email(apartment.getHomeowner().getEmail())
                                .firstName(apartment.getHomeowner().getFirstName())
                                .lastName(apartment.getHomeowner().getLastName())
                                .phoneNumber(apartment.getHomeowner().getPhoneNumber())
                                .profileImage(ProfileImagedto.fromEntity(apartment.getHomeowner().getProfileImage()))

                        .build():null
                )
                .distances(
                        apartment.getDistanceList()!=null ?
                                apartment.getDistanceList().stream()
                                        .map(Distancedto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .citydto(
                        Citydto.builder()
                                .id(apartment.getCity().getId())
                                .name(apartment.getCity().getName())
                                .postalCode(apartment.getCity().getPostalCode())
                                .build()
                )
                .images(
                        apartment.getImageList()!=null ?
                                apartment.getImageList().stream()
                                        .map(ApartmentImagedto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .favourites(
                        apartment.getFavouritesSet()!=null?
                                apartment.getFavouritesSet().stream()
                                        .map(Favouritesdto::fromEntity)
                                        .collect(Collectors.toSet()):null
                )
                .rentalDetails(
                        apartment.getRentalDetailsList()!=null?
                                apartment.getRentalDetailsList().stream()
                                        .map(RentalDetailsdto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .rentalFeedbacks(
                        apartment.getRentalFeedbackSet()!=null?
                                apartment.getRentalFeedbackSet().stream()
                                        .map(RentalFeedbackdto::fromEntity)
                                        .collect(Collectors.toSet()):null
                )
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
        //apartment.setHomeowner(Homeownerdto.toEntity(apartmentdto.getHomeowner()));
        //apartment.setCity(Citydto.toEntity(apartmentdto.getCitydto()));

        return apartment;
    }
}
