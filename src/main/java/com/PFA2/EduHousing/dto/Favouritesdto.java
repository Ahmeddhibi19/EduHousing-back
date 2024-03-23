package com.PFA2.EduHousing.dto;


import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.Favourites;
import com.PFA2.EduHousing.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Collectors;

@Data
@Builder
public class Favouritesdto {

    private Integer id;


    private String description;


    private Studentdto student;


    private Apartmentdto apartment;

    public static Favouritesdto fromEntity(Favourites favourites){
        if(favourites==null){
            return null;
        }
        return Favouritesdto.builder()
                .id(favourites.getId())
                .description(favourites.getDescription())
                .student(
                        Studentdto.builder()
                                .id(favourites.getStudent().getId())
                                .firstName(favourites.getStudent().getFirstName())
                                .lastName(favourites.getStudent().getLastName())
                                .email(favourites.getStudent().getEmail())
                                .build()
                )
                .apartment(
                        Apartmentdto.builder()
                                .id(favourites.getApartment().getId())
                                .address(favourites.getApartment().getAddress())
                                .type(favourites.getApartment().getType())
                                .images(favourites.getApartment().getImageList().stream().map(ApartmentImagedto::fromEntity).collect(Collectors.toList()))
                                .build()
                )
                .build();
    }

    public static Favourites toEntity(Favouritesdto favouritesdto){
        if(favouritesdto==null){
            return  null;
        }
        Favourites favourites= new Favourites();
        favourites.setId(favouritesdto.getId());
        favourites.setDescription(favouritesdto.getDescription());
        favourites.setStudent(Studentdto.toEntity(favouritesdto.getStudent()));
        favourites.setApartment(Apartmentdto.toEntity(favouritesdto.getApartment()));
        return favourites ;
    }
}
