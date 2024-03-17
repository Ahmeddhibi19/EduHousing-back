package com.PFA2.EduHousing.dto;


import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.City;
import com.PFA2.EduHousing.model.College;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Citydto {

    private Integer id;

    private String name;

    private String postalCode;

    @JsonIgnore
    private List<Apartmentdto> apartments = new ArrayList<>();

    @JsonIgnore
    private List<Collegedto> colleges = new ArrayList<>();

    public static Citydto fromEntity(City city){
        if(city==null){
            return  null;
        }
        return Citydto.builder()
                .id(city.getId())
                .name(city.getName())
                .postalCode(city.getPostalCode())
                .build();
    }

    public static City toEntity(Citydto citydto){
        if(citydto==null){
            return  null;
        }
        City city=new City();
        city.setId(citydto.getId());
        city.setName(citydto.getName());
        city.setPostalCode(citydto.getPostalCode());
        return city;
    }
}
