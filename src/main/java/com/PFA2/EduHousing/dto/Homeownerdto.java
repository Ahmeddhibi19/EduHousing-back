package com.PFA2.EduHousing.dto;


import com.PFA2.EduHousing.Utils.ImageUtils;
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
import java.util.stream.Collectors;

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

    private ConnexionStatus status;

    private Boolean isEnabled;


    private ProfileImagedto profileImage;


    private List<Apartmentdto> apartments;


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
               /* .password(homeowner.getPassword())*/
                .phoneNumber(homeowner.getPhoneNumber())
                .role(homeowner.getRole())
                .address(homeowner.getAddress())
                .isEnabled(homeowner.getIsEnabled())
                .status(homeowner.getStatus())
                .profileImage(
                        homeowner.getProfileImage()!=null?
                        ProfileImagedto.builder()
                        .id(homeowner.getProfileImage().getId())
                        .data(ImageUtils.decompressImage(homeowner.getProfileImage().getData()))
                        .type(homeowner.getProfileImage().getType())
                        .build():null
                )
                .apartments(
                        homeowner.getApartmentList()!=null?
                                homeowner.getApartmentList().stream()
                                        .map(Apartmentdto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .applicationFeedback(
                        homeowner.getApplicationFeedback()!=null?
                            ApplicationFeedbackdto.fromEntity(homeowner.getApplicationFeedback()):null
                )
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
        homeowner.setAddress(homeownerdto.getAddress());
        homeowner.setIsEnabled(homeownerdto.getIsEnabled());
        homeowner.setStatus(homeownerdto.getStatus());
        homeowner.setProfileImage(
                homeownerdto.getProfileImage()!=null?
                        ProfileImagedto.toEntity(homeownerdto.getProfileImage()):null
                );
        homeowner.setApplicationFeedback(
                homeownerdto.getApplicationFeedback()!=null?
                        ApplicationFeedbackdto.toEntity(homeownerdto.getApplicationFeedback()):null
        );

        return homeowner;
    }

}
