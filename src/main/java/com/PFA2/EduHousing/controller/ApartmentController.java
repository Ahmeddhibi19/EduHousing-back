package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.ApartmentApi;
import com.PFA2.EduHousing.dto.Apartmentdto;
import com.PFA2.EduHousing.services.ApartmentService.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ApartmentController implements ApartmentApi {
    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(
            ApartmentService apartmentService
    ){
        this.apartmentService=apartmentService;
    }
    @Override
    public Apartmentdto save(Apartmentdto apartmentdto, Integer homeownerId, Integer cityId) {
        return apartmentService.save(apartmentdto,homeownerId,cityId);
    }

    @Override
    public Apartmentdto findById(Integer id) {
        return apartmentService.findById(id);
    }

    @Override
    public List<Apartmentdto> findByCityId(Integer cityId) {
        return apartmentService.findByCityId(cityId);
    }

    @Override
    public List<Apartmentdto> findByHomeownerId(Integer id) {
        return apartmentService.findByHomeownerId(id);
    }

    @Override
    public List<Apartmentdto> findAllByType(String type) {
        return apartmentService.findAllByType(type);
    }

    @Override
    public List<Apartmentdto> findAllByCityIdAndHomeownerId(Integer cityId, Integer homeownerId) {
        return apartmentService.findAllByCityIdAndHomeownerId(cityId,homeownerId);
    }

    @Override
    public List<Apartmentdto> findAllByCityIdAndType(Integer cityId, String type) {
        return apartmentService.findAllByCityIdAndType(cityId,type);
    }

    @Override
    public List<Apartmentdto> findAllByHomeownerIdAndType(Integer id, String type) {
        return apartmentService.findAllByHomeownerIdAndType(id,type);
    }

    @Override
    public List<Apartmentdto> findAllByHomeownerIdAndIsRented(Integer homeownerId) {
        return apartmentService.findAllByHomeownerIdAndIsRented(homeownerId);
    }

    @Override
    public List<Apartmentdto> findAllByCityIdNotIsRented(Integer cityId) {
        return apartmentService.findAllByCityIdNotIsRented(cityId);
    }

    @Override
    public void deleteById(Integer id) {
        apartmentService.deleteById(id);
    }

    @Override
    public Apartmentdto update(Apartmentdto apartmentdto) {
        return apartmentService.update(apartmentdto);
    }

    @Override
    public List<Apartmentdto> findApartmentByRatingLessThanAndCityId(Double rating, Integer cityId) {
        return apartmentService.findApartmentByRatingLessThanAndCityId(rating,cityId);
    }

    @Override
    public List<Apartmentdto> findApartmentByRatingGreaterThanAndCityId(Double rating, Integer cityId) {
        return apartmentService.findApartmentByRatingGreaterThanAndCityId(rating,cityId);
    }

    @Override
    public List<Apartmentdto> findApartmentsByCollegeAndSameCity(Integer collegeId) {
        return apartmentService.findApartmentsByCollegeAndSameCity(collegeId);
    }

    @Override
    public List<Apartmentdto> findApartmentsByCollegeAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue) {
        return apartmentService.findApartmentsByCollegeAndDistanceLessThan(collegeId,distanceValue);
    }

    @Override
    public List<Apartmentdto> findApartmentsByCollegeAndCityAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue) {
        return apartmentService.findApartmentsByCollegeAndCityAndDistanceLessThan(collegeId,distanceValue);
    }
    @Override
    public List<Apartmentdto> findAll(){
        return apartmentService.findAll();
    }
}
