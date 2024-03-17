package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.Admin;
import com.PFA2.EduHousing.model.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
public class Admindto  {

    private  Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private Roles role;

    private Boolean isActivated;

    @JsonIgnore
    private ProfileImagedto profileImage;

    public static Admindto fromEntity(Admin admin){
        if(admin == null){
            return null;
        }
        return Admindto.builder()
                .id(admin.getId())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .phoneNumber(admin.getPhoneNumber())
                .role(admin.getRole())
                .isActivated(admin.getIsActivated())
                .build();
    }


    public static Admin toEntity(Admindto admindto){
        if (admindto==null){
            return null;
        }
        Admin admin=new Admin();
        admin.setId(admindto.getId());
        admin.setFirstName(admindto.getFirstName());
        admin.setLastName(admindto.getLastName());
        admin.setEmail(admindto.getEmail());
        admin.setPassword(admindto.getPassword());
        admin.setPhoneNumber(admindto.getPhoneNumber());
        admin.setIsActivated(admindto.getIsActivated());
        admin.setRole(admindto.getRole());

        return admin;
    }

}
