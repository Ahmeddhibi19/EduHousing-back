package com.PFA2.EduHousing.services.ApartmentImageService;

import com.PFA2.EduHousing.dto.ApartmentImagedto;

import java.util.List;

public interface ApartmentImageService {

    public ApartmentImagedto save(ApartmentImagedto apartmentImagedto,Integer apartmentId);

    public ApartmentImagedto findById(Integer id);

    public List<ApartmentImagedto> findAllByApartmentId(Integer id);
    public List<ApartmentImagedto> findAll();
    public void deleteById(Integer id);

}