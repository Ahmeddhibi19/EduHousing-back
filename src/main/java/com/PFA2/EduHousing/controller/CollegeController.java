package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.CollegeApi;
import com.PFA2.EduHousing.dto.Collegedto;
import com.PFA2.EduHousing.services.CollegeService.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollegeController implements CollegeApi {
    private final CollegeService collegeService;
    @Autowired
    public CollegeController(CollegeService collegeService){
        this.collegeService=collegeService;
    }
    @Override
    public Collegedto save(Collegedto collegedto, Integer cityId) {
        return collegeService.save(collegedto,cityId);
    }

    @Override
    public Collegedto findById(Integer Id) {
        return collegeService.findById(Id);
    }

    @Override
    public Collegedto findByName(String name) {
        return collegeService.findByName(name);
    }

    @Override
    public List<Collegedto> findAll() {
        return collegeService.findAll();
    }

    @Override
    public List<Collegedto> findByCityId(Integer id) {
        return collegeService.findByCityId(id);
    }

    @Override
    public Collegedto update(Collegedto collegedto) {
        return collegeService.update(collegedto);
    }

    @Override
    public void deleteById(Integer id) {
        collegeService.deleteById(id);
    }

    @Override
    public List<Collegedto> findCollegesByApartmentAndSameCity(Integer apartmentId) {
        return collegeService.findCollegesByApartmentAndSameCity(apartmentId);
    }

    @Override
    public Integer findCollegesByStudentId(Integer id) {
        return collegeService.getCollegeByStudentId(id);
    }
}
