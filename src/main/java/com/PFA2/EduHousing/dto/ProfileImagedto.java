package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.ProfileImage;
import com.PFA2.EduHousing.model.User;
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

    private byte[] data;

    @JsonIgnore
    private Studentdto studentdto;

    @JsonIgnore
    private Homeownerdto homeownerdto;

    @JsonIgnore
    private Admindto admindto;

    public static ProfileImagedto fromEntity(ProfileImage profileImage){
        if(profileImage==null){
            return null;
        }

        return ProfileImagedto.builder()
                .id(profileImage.getId())
                .data(profileImage.getData())
                .build();
    }

    public static ProfileImage toEntity(ProfileImagedto profileImagedto){
        if(profileImagedto==null){
            return  null;
        }
        ProfileImage profileImage= new ProfileImage();
        profileImage.setId(profileImagedto.getId());
        profileImage.setData(profileImagedto.getData());
        return profileImage;
    }
}
