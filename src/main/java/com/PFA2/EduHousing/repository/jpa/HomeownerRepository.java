package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.Homeowner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("homeowner")
public interface HomeownerRepository extends JpaRepository<Homeowner,Integer> {

    public Optional<Homeowner> findHomeownerByEmail(String email);

    public Optional<Homeowner> findHomeownerByPhoneNumber(String phoneNumber);

    public List<Homeowner> findAllByFirstName(String firstName);

    public void deleteHomeownerByEmail(String email);

    public void deleteHomeownerByPhoneNumber(String phoneNumber);
}
