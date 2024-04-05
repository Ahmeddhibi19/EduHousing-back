package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("admin")
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    public Optional<Admin> findAdminByEmail(String email);
    public Optional<Admin> findAdminByPhoneNumber(String phoneNumber);
    public List<Admin> findAllByFirstName(String firstName);
    public void deleteAdminByEmail(String email);
    public void deleteAdminByPhoneNumber(String phoneNumber);

}
