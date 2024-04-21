package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.Utils.ImageUtils;
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

    private String name;
    private String type;

    private byte[] data;


    private Apartmentdto apartment;

    public static ApartmentImagedto fromEntity(ApartmentImage apartmentImage){
        if(apartmentImage==null){
            return  null;
        }
        return ApartmentImagedto.builder()
                .id(apartmentImage.getId())
                .data( ImageUtils.decompressImage(apartmentImage.getData()))
                .name(apartmentImage.getName())
                .type(apartmentImage.getType())
                /*.apartment(
                        Apartmentdto.builder()
                                .id(apartmentImage.getApartment().getId())
                                .type(apartmentImage.getApartment().getType())
                                .address(apartmentImage.getApartment().getAddress())
                                .build()
                )*/
                .build();
    }

    public static ApartmentImage toEntity(ApartmentImagedto apartmentImagedto){
        if(apartmentImagedto==null){
            return null;
        }
        ApartmentImage apartmentImage=new ApartmentImage();
        apartmentImage.setId(apartmentImagedto.getId());
        apartmentImage.setData(ImageUtils.compressImage(apartmentImagedto.getData()));
        apartmentImage.setType(apartmentImagedto.getType());
        apartmentImage.setName(apartmentImagedto.getName());
        apartmentImage.setApartment(Apartmentdto.toEntity(apartmentImagedto.getApartment()));
        return apartmentImage;
    }
}
