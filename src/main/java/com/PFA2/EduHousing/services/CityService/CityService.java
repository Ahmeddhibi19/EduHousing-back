package com.PFA2.EduHousing.services.CityService;

import com.PFA2.EduHousing.dto.Citydto;
import org.hibernate.annotations.SecondaryRow;
import org.hibernate.type.descriptor.sql.internal.CapacityDependentDdlType;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CityService {

    public Citydto save(Citydto citydto);
    public Citydto findById(Integer id);
    public Citydto findByName(String name);
    public Citydto findByPostalCode(String postalCode);
    public List<Citydto> findAll();
    public Citydto update(Citydto citydto);
    public void deleteById(Integer id);
    public Integer getCityByCollegeId(Integer id);
}
