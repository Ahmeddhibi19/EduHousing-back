package com.PFA2.EduHousing.services.AdminService;

import com.PFA2.EduHousing.dto.Admindto;

import java.util.List;

public interface AdminService {

    public Admindto save(Admindto admindto);
    public Admindto findById(Integer id);
    public Admindto findByEmail(String Email);
    public Admindto findByPhoneNumber(String phoneNumber);
    public List<Admindto> findByFirstName(String firstName);
    public List<Admindto> findAll();
    public Admindto update(Admindto admindto);
    public void deleteById(Integer id);
    public void deleteByPhoneNumber(String phoneNumber);
    public void deleteByEmail(String email);

}
