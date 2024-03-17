package com.PFA2.EduHousing.services.ApartmentService;

import com.PFA2.EduHousing.dto.Apartmentdto;
import com.PFA2.EduHousing.model.Apartment;

import java.math.BigDecimal;
import java.util.List;

public interface ApartmentService {

    public Apartmentdto save(Apartmentdto apartmentdto,Integer homeownerId,Integer cityId);

    public Apartmentdto findById(Integer id);
    public List<Apartmentdto> findByCityId(Integer cityId);
    public List<Apartmentdto> findByHomeownerId(Integer id);
    public List<Apartmentdto> findAllByType(String type);
    public List<Apartmentdto> findAllByCityIdAndHomeownerId(Integer cityId,Integer homeownerId);
    public List<Apartmentdto> findAllByCityIdAndType(Integer id,String type);
    public List<Apartmentdto> findAllByHomeownerIdAndType(Integer id,String type);
    public List<Apartmentdto> findAllByHomeownerIdAndIsRented(Integer homeownerId);
    public List<Apartmentdto> findAllByCityIdNotIsRented(Integer cityId);

    public void deleteById(Integer id);

    public Apartmentdto update(Apartmentdto apartmentdto);

    public List<Apartmentdto> findApartmentByRatingLessThanAndCityId(Double rating,Integer cityId);

    public List<Apartmentdto> findApartmentByRatingGreaterThanAndCityId(Double rating,Integer cityId);
    public List<Apartmentdto> findApartmentsByCollegeAndSameCity(Integer collegeId);
    public List<Apartmentdto> findApartmentsByCollegeAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue);
    public List<Apartmentdto> findApartmentsByCollegeAndCityAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue);


}
