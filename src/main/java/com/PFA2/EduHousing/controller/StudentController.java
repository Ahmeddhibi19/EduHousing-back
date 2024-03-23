package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.StudentApi;
import com.PFA2.EduHousing.dto.Studentdto;
import com.PFA2.EduHousing.services.StudentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController implements StudentApi {

    private final StudentService studentService;
    @Autowired
    public StudentController(
            StudentService studentService
    ){
        this.studentService=studentService;
    }

    @Override
    public Studentdto save(Studentdto studentdto, Integer collegeId) {
        return studentService.save(studentdto,collegeId);
    }

    @Override
    public Studentdto findById(Integer id) {
        return studentService.findById(id);
    }

    @Override
    public Studentdto findByEmail(String email) {
        return studentService.findByEmail(email);
    }

    @Override
    public List<Studentdto> findByFirstName(String firstName) {
        return studentService.findByFirstName(firstName);
    }

    @Override
    public Studentdto findByPhoneNumber(String phoneNumber) {
        return studentService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Studentdto update(Studentdto studentdto) {
        return studentService.update(studentdto);
    }

    @Override
    public Studentdto updateCollege(Integer studentId, Integer collegeId) {
        return studentService.updateCollege(studentId,collegeId);
    }

    @Override
    public void deleteById(Integer id) {
        studentService.deleteById(id);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        studentService.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        studentService.deleteByEmail(email);
    }

    @Override
    public List<Studentdto> findAll() {
        return studentService.findAll();
    }

    @Override
    public List<Studentdto> findAllByCollegeId(Integer collegeId) {
        return studentService.findAllByCollegeId(collegeId);
    }
}
