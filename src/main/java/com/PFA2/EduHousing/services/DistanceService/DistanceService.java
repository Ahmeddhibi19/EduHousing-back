package com.PFA2.EduHousing.services.DistanceService;

import com.PFA2.EduHousing.dto.Apartmentdto;
import com.PFA2.EduHousing.dto.Collegedto;
import com.PFA2.EduHousing.dto.Distancedto;
import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.College;
import com.PFA2.EduHousing.model.Distance;

import java.math.BigDecimal;
import java.util.List;

public interface DistanceService {
    public Distancedto findByid(Integer id);
    public List<Distancedto> findAll();
    public List<Distancedto> findAllByApartmentId(Integer apartmentId);
    public List<Distancedto> findAllByCollegeId(Integer collegeId);
   /* public List<Apartmentdto> findApartmentsByCollegeAndSameCity(Integer collegeId);
    public List<Collegedto> findCollegesByApartmentAndSameCity(Integer apartmentId);
    public List<Apartmentdto> findApartmentsByCollegeAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue);
    public List<Collegedto> findCollegesByApartmentAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue);
    public List<Apartmentdto> findApartmentsByCollegeAndCityAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue);
    public List<Collegedto> findCollegesByApartmentAndCityAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue);*/
   public List<Distancedto> findAllByInSameCity(Integer cityId);


}
