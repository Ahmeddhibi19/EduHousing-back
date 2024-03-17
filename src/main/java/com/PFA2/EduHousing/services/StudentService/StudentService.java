package com.PFA2.EduHousing.services.StudentService;

import com.PFA2.EduHousing.dto.Studentdto;

import java.util.List;

public interface StudentService {

    Studentdto save(Studentdto studentdto , Integer collegeId);

    Studentdto findById(Integer id);

    Studentdto findByEmail(String email);

    List<Studentdto> findByFirstName(String firstName);

    Studentdto findByPhoneNumber(String phoneNumber);

    Studentdto update(Studentdto studentdto);
    Studentdto updateCollege(Integer studentId,Integer collegeId);

    void deleteById(Integer id);

    void deleteByPhoneNumber(String phoneNumber);

    void deleteByEmail(String email);

    List<Studentdto> findAll();

    List<Studentdto> findAllByCollegeId(Integer collegeId);
}
