package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.City;
import com.PFA2.EduHousing.model.College;
import com.PFA2.EduHousing.model.Distance;
import com.PFA2.EduHousing.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Collegedto {

    private Integer id;


    private BigDecimal latitude;


    private BigDecimal longitude;


    private String address;


    private String name;

    @JsonIgnore
    private List<Distancedto> distances = new ArrayList<>();

    @JsonIgnore
    private Citydto city;

    @JsonIgnore
    private List<Studentdto> students = new ArrayList<>();

    public static Collegedto fromEntity(College college){
        if(college==null){
            return null;
        }
        return Collegedto.builder()
                .id(college.getId())
                .name(college.getName())
                .address(college.getAddress())
                .latitude(college.getLatitude())
                .longitude(college.getLongitude())
                .build();
    }

    public static College toEntity(Collegedto collegedto){
        if(collegedto==null){
            return null;
        }
        College college = new College();
        college.setId(collegedto.getId());
        college.setName(collegedto.getName());
        college.setAddress(collegedto.getAddress());
        college.setLatitude(collegedto.getLatitude());
        college.setLongitude(collegedto.getLongitude());
        return college;
    }
}
