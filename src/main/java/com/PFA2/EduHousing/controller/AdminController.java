package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.AdminApi;
import com.PFA2.EduHousing.dto.Admindto;
import com.PFA2.EduHousing.services.AdminService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController implements AdminApi {
    private final AdminService adminService;

    @Autowired
    public AdminController(
            AdminService adminService
    ){
        this.adminService=adminService;
    }
    @Override
    public Admindto save(Admindto admindto) {
        return adminService.save(admindto);
    }

    @Override
    public Admindto findById(Integer id) {
        return adminService.findById(id);
    }

    @Override
    public Admindto findByEmail(String email) {
        return adminService.findByEmail(email);
    }

    @Override
    public Admindto findByPhoneNumber(String phoneNumber) {
        return adminService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Admindto> findByFirstName(String firstName) {
        return adminService.findByFirstName(firstName);
    }

    @Override
    public List<Admindto> findAll() {
        return adminService.findAll();
    }

    @Override
    public Admindto update(Admindto admindto) {
        return adminService.update(admindto);
    }

    @Override
    public void deleteById(Integer id) {
        adminService.deleteById(id);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        adminService.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        adminService.deleteByEmail(email);
    }
}
