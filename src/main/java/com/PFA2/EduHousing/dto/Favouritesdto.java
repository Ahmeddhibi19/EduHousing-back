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

@Data
@Builder
public class Favouritesdto {

    private Integer id;


    private String description;

    @JsonIgnore
    private Studentdto student;

    @JsonIgnore
    private Apartmentdto apartment;

    public static Favouritesdto fromEntity(Favourites favourites){
        if(favourites==null){
            return null;
        }
        return Favouritesdto.builder()
                .id(favourites.getId())
                .description(favourites.getDescription())
                .build();
    }

    public static Favourites toEntity(Favouritesdto favouritesdto){
        if(favouritesdto==null){
            return  null;
        }
        Favourites favourites= new Favourites();
        favourites.setId(favouritesdto.getId());
        favourites.setDescription(favouritesdto.getDescription());
        return favourites ;
    }
}
