package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.HomeownerApi;
import com.PFA2.EduHousing.dto.Homeownerdto;
import com.PFA2.EduHousing.services.HomeownerService.HomeownerService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeownerController implements HomeownerApi {
    private final HomeownerService homeownerService;

    public HomeownerController(
            HomeownerService homeownerService
    ){
        this.homeownerService=homeownerService;
    }
    @Override
    public Homeownerdto save(Homeownerdto homeownerdto) {
        return homeownerService.save(homeownerdto);
    }

    @Override
    public Homeownerdto findById(Integer homeownerId) {
        return homeownerService.findById(homeownerId);
    }

    @Override
    public Homeownerdto findByEmail(String email) {
        return homeownerService.findByEmail(email);
    }

    @Override
    public List<Homeownerdto> findByFirstName(String firstName) {
        return homeownerService.findByFirstName(firstName);
    }

    @Override
    public List<Homeownerdto> findAll() {
        return homeownerService.findAll();
    }

    @Override
    public Homeownerdto findByPhoneNumber(String phoneNumber) {
        return homeownerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Homeownerdto update(Homeownerdto homeownerdto) {
        return homeownerService.update(homeownerdto);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        homeownerService.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        homeownerService.deleteByEmail(email);
    }

    @Override
    public void deleteById(Integer id) {
        homeownerService.deleteById(id);
    }
}
