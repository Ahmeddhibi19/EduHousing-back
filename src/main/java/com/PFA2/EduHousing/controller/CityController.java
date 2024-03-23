package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.CityApi;
import com.PFA2.EduHousing.dto.Citydto;
import com.PFA2.EduHousing.services.CityService.CityService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController implements CityApi {
    private final CityService cityService;
    public CityController(CityService cityService){
        this.cityService=cityService;
    }
    @Override
    public Citydto save(Citydto citydto) {
        return cityService.save(citydto);
    }

    @Override
    public Citydto findById(Integer id) {
        return cityService.findById(id);
    }

    @Override
    public Citydto findByName(String name) {
        return cityService.findByName(name);
    }

    @Override
    public Citydto findByPostalCode(String postalCode) {
        return cityService.findByPostalCode(postalCode);
    }

    @Override
    public List<Citydto> findAll() {
        return cityService.findAll();
    }

    @Override
    public Citydto update(Citydto citydto) {
        return cityService.update(citydto);
    }

    @Override
    public void deleteById(Integer id) {
        cityService.deleteById(id);
    }
}
