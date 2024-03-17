package com.PFA2.EduHousing.services.HomeownerService;

import com.PFA2.EduHousing.dto.Homeownerdto;

import java.util.List;

public interface HomeownerService {

    public Homeownerdto save(Homeownerdto homeownerdto);

    public Homeownerdto findById(Integer homeownerId);

    public Homeownerdto findByEmail(String email);

    public List<Homeownerdto> findByFirstName(String firstName);

    public List<Homeownerdto> findAll();

    public Homeownerdto findByPhoneNumber(String phoneNumber);

    public Homeownerdto update(Homeownerdto homeownerdto);
    void deleteByPhoneNumber(String phoneNumber);

    void deleteByEmail(String email);

    void deleteById(Integer id);
}
