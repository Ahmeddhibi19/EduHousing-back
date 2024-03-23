package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.Utils.ImageUtils;
import com.PFA2.EduHousing.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileImagedto {

    private Integer id;

    private String name;
    private String type;

    private byte[] data;


    private Studentdto studentdto;


    private Homeownerdto homeownerdto;


    private Admindto admindto;

    public static ProfileImagedto fromEntity(ProfileImage profileImage){
        if(profileImage==null){
            return null;
        }

        return ProfileImagedto.builder()
                .id(profileImage.getId())
                .name(profileImage.getName())
                .type(profileImage.getType())
                .data(ImageUtils.decompressImage(profileImage.getData()))
                .admindto(
                        profileImage.getUser() instanceof Admin?
                                Admindto.builder()
                                        .id(profileImage.getUser().getId())
                                        .email(profileImage.getUser().getEmail())
                                        .firstName(profileImage.getUser().getFirstName())
                                        .lastName(profileImage.getUser().getLastName())
                                        .build() : null
                )
                .studentdto( profileImage.getUser() instanceof Student?
                        Studentdto.builder()
                                .id(profileImage.getUser().getId())
                                .email(profileImage.getUser().getEmail())
                                .firstName(profileImage.getUser().getFirstName())
                                .lastName(profileImage.getUser().getLastName())
                                .build() : null)
                .homeownerdto(
                        profileImage.getUser() instanceof Homeowner?
                            Homeownerdto.builder()
                                .id(profileImage.getUser().getId())
                                .email(profileImage.getUser().getEmail())
                                .firstName(profileImage.getUser().getFirstName())
                                .lastName(profileImage.getUser().getLastName())
                                .build() : null
                )
                .build();
    }

    public static ProfileImage toEntity(ProfileImagedto profileImagedto){
        if (profileImagedto == null) {
            return null;
        }
        ProfileImage profileImage = new ProfileImage();
        profileImage.setId(profileImagedto.getId());
        profileImage.setData(ImageUtils.compressImage(profileImagedto.getData()));
        profileImage.setType(profileImagedto.getType());
        profileImage.setName(profileImagedto.getName());

        User user = null;
        if (profileImagedto.getAdmindto() != null) {
            user = Admindto.toEntity(profileImagedto.getAdmindto());
        } else if (profileImagedto.getStudentdto() != null) {
            user = Studentdto.toEntity(profileImagedto.getStudentdto());
        } else if (profileImagedto.getHomeownerdto() != null) {
            user = Homeownerdto.toEntity(profileImagedto.getHomeownerdto());
        }
        profileImage.setUser(user);

        return profileImage;
    }
}
