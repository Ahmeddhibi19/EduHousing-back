package com.PFA2.EduHousing.services.CollegeService;

import com.PFA2.EduHousing.dto.Citydto;
import com.PFA2.EduHousing.dto.Collegedto;
import com.PFA2.EduHousing.model.College;

import java.math.BigDecimal;
import java.util.List;

public interface CollegeService {

    public Collegedto save(Collegedto collegedto, Integer cityId);
    public Collegedto findById(Integer Id);
    public Collegedto findByName(String name);
    public List<Collegedto> findAll();
    public List<Collegedto> findByCityId(Integer id);
    public Collegedto update(Collegedto collegedto);
    public void deleteById(Integer id);
    public List<Collegedto> findCollegesByApartmentAndSameCity(Integer apartmentId);
   /* public List<Collegedto> findCollegesByApartmentAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue);
    public List<Collegedto> findCollegesByApartmentAndCityAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue);*/
}
