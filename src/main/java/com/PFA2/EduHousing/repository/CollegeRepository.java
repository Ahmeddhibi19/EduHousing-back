package com.PFA2.EduHousing.repository;

import com.PFA2.EduHousing.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<College,Integer> {

    public Optional<College> findCollegeByAddress(String address);

    public Optional<College> findCollegeByName(String name);

    public List<College> findAllByCityId(Integer cityId);
    @Query("SELECT d.college FROM Distance d WHERE d.apartment.id =: apartmentId AND d.apartment.city.id = d.college.city.id")
    public List<College> findCollegesByApartmentAndSameCity(Integer apartmentId);
    @Query("SELECT d.college FROM Distance d WHERE d.apartment.id = :apartmentId AND d.distanceValue < :distanceValue")
    public List<College> findCollegesByApartmentAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue);
    @Query("SELECT d.college FROM Distance d WHERE d.apartment.id = :apartmentId AND d.apartment.city.id = d.college.city.id AND d.distanceValue < :distanceValue")
    public List<College> findCollegesByApartmentAndCityAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue);
}
