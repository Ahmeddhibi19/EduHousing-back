package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("student")
public interface StudentRepository extends JpaRepository<Student,Integer> {
    public Optional<Student> findStudentByEmail(String email);

    public Optional<Student> findStudentByPhoneNumber(String phoneNumber);

    public List<Student> findAllByFirstName(String firstName);

    public void deleteStudentByEmail(String email);

    public void deleteStudentByPhoneNumber(String phoneNumber);

    @Query("select s from Student s where s.college.id=:collegeId")
    public List<Student> findAllByCollegeId(Integer collegeId);
}
