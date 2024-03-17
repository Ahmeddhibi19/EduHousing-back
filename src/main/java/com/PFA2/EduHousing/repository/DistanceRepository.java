package com.PFA2.EduHousing.repository;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.College;
import com.PFA2.EduHousing.model.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DistanceRepository extends JpaRepository<Distance,Integer> {

    public List<Distance> findAllByApartmentId(Integer apartmentId);

    public List<Distance> findAllByCollegeId(Integer collegeId);

   /* *//*@Query("SELECT d.apartment FROM Distance d WHERE d.college.id =: collegeId AND d.college.city.id = d.apartment.city.id")
    public List<Apartment> findApartmentsByCollegeAndSameCity(Integer collegeId);*//*


    @Query("SELECT d.college FROM Distance d WHERE d.apartment.id =: apartmentId AND d.apartment.city.id = d.college.city.id")
    public List<College> findCollegesByApartmentAndSameCity(Integer apartmentId);

    *//*@Query("SELECT d.apartment FROM Distance d WHERE d.college.id = :collegeId AND d.distanceValue < :distanceValue")
    public List<Apartment> findApartmentsByCollegeAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue);*//*


    @Query("SELECT d.college FROM Distance d WHERE d.apartment.id = :apartmentId AND d.distanceValue < :distanceValue")
    public List<College> findCollegesByApartmentAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue);



   *//* @Query("SELECT d.apartment FROM Distance d WHERE d.college.id = :collegeId AND d.college.city.id = d.apartment.city.id AND d.distanceValue < :distanceValue")
    public List<Apartment> findApartmentsByCollegeAndCityAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue);*//*




    @Query("SELECT d.college FROM Distance d WHERE d.apartment.id = :apartmentId AND d.apartment.city.id = d.college.city.id AND d.distanceValue < :distanceValue")
    public List<College> findCollegesByApartmentAndCityAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue);*/
    @Query("select d from Distance d where d.apartment.city.id=:cityId and d.college.city.id=:cityId")
    public List<Distance> findAllInSameCity(Integer cityId);

}
