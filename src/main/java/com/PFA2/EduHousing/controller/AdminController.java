package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.Utils.JwtUtils;
import com.PFA2.EduHousing.controller.api.AdminApi;
import com.PFA2.EduHousing.dto.Admindto;
import com.PFA2.EduHousing.model.auth.ExtendedUser;
import com.PFA2.EduHousing.services.AdminService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController implements AdminApi {
    private final AdminService adminService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AdminController(
            AdminService adminService,
            JwtUtils jwtUtils
    ){
        this.adminService=adminService;
        this.jwtUtils=jwtUtils;
    }
    @Override
    public String save(Admindto admindto) {
        Admindto admin =adminService.save(admindto);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority (admin.getRole().toString()));
        ExtendedUser user = new ExtendedUser(admin.getEmail(), admin.getPassword(), authorities);
        return jwtUtils.generateToken(user);
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
