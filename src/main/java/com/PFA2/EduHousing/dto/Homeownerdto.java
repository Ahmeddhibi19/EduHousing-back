package com.PFA2.EduHousing.dto;


import com.PFA2.EduHousing.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Homeownerdto  {

    private  Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private Roles role;

    private String address;

    @JsonIgnore
    private ProfileImagedto profileImage;

    @JsonIgnore
    private List<Apartmentdto> apartments = new ArrayList<>();

    @JsonIgnore
    private ApplicationFeedbackdto applicationFeedback;

    public static Homeownerdto fromEntity(Homeowner homeowner){
        if(homeowner == null){
            return null;
        }
        return Homeownerdto.builder()
                .id(homeowner.getId())
                .firstName(homeowner.getFirstName())
                .lastName(homeowner.getLastName())
                .email(homeowner.getEmail())
                .password(homeowner.getPassword())
                .phoneNumber(homeowner.getPhoneNumber())
                .role(homeowner.getRole())
                .address(homeowner.getAddress())
                .build();
    }


    public static Homeowner toEntity(Homeownerdto homeownerdto){
        if (homeownerdto==null){
            return null;
        }
        Homeowner homeowner=new Homeowner();
        homeowner.setId(homeownerdto.getId());
        homeowner.setFirstName(homeownerdto.getFirstName());
        homeowner.setLastName(homeownerdto.getLastName());
        homeowner.setEmail(homeownerdto.getEmail());
        homeowner.setPassword(homeownerdto.getPassword());
        homeowner.setPhoneNumber(homeownerdto.getPhoneNumber());
        homeowner.setRole(homeownerdto.getRole());

        return homeowner;
    }

}
