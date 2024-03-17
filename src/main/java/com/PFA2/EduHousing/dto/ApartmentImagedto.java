package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.ApartmentImage;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApartmentImagedto {

    private Integer id;


    private byte[] data;


    private Apartmentdto apartment;

    public static ApartmentImagedto fromEntity(ApartmentImage apartmentImage){
        if(apartmentImage==null){
            return  null;
        }
        return ApartmentImagedto.builder()
                .id(apartmentImage.getId())
                .data(apartmentImage.getData())
                .build();
    }

    public static ApartmentImage toEntity(ApartmentImagedto apartmentImagedto){
        if(apartmentImagedto==null){
            return null;
        }
        ApartmentImage apartmentImage=new ApartmentImage();
        apartmentImage.setId(apartmentImagedto.getId());
        apartmentImage.setData(apartmentImagedto.getData());
        return apartmentImage;
    }
}
