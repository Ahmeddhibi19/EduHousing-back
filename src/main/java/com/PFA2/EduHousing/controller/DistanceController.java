package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.DistanceApi;
import com.PFA2.EduHousing.dto.Distancedto;
import com.PFA2.EduHousing.services.DistanceService.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DistanceController implements DistanceApi {
    private final DistanceService distanceService;
    @Autowired
    public DistanceController(
            DistanceService distanceService
    ){
        this.distanceService=distanceService;
    }
    @Override
    public Distancedto findById(Integer id) {
        return distanceService.findByid(id);
    }

    @Override
    public List<Distancedto> findAll() {
        return distanceService.findAll();
    }

    @Override
    public List<Distancedto> findAllByApartmentId(Integer apartmentId) {
        return distanceService.findAllByApartmentId(apartmentId);
    }

    @Override
    public List<Distancedto> findAllByCollegeId(Integer collegeId) {
        return distanceService.findAllByCollegeId(collegeId);
    }

    @Override
    public List<Distancedto> findAllByInSameCity(Integer cityId) {
        return distanceService.findAllByInSameCity(cityId);
    }
}
