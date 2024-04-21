package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.City;
import com.PFA2.EduHousing.model.Homeowner;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment,Integer> {

    public List<Apartment> findAllByCityId(Integer cityId);

    public List<Apartment> findAllByHomeownerId(Integer homeownerId);

    public List<Apartment> findAllByType(String type);

    public List<Apartment> findAllByCityIdAndHomeownerId(Integer cityId,Integer homeownerId);
    public List<Apartment> findAllByCityIdAndType(Integer cityId, String type);

    public List<Apartment> findAllByHomeownerIdAndType(Integer homeownerId,String type);

    @Query("select a from Apartment a where a.homeowner.id=:homeownerId and a.isRented=true")
    public List<Apartment> findAllByHomeownerIdAndIsRented(Integer homeownerId);
    @Query("select a from Apartment a where a.city.id=:cityId and a.isRented=false")
    public List<Apartment> findAllByCityIdNotIsRented(Integer cityId);

    public Optional<Apartment> findApartmentByAddress(String address);

    public List<Apartment> findAllByRatingLessThanAndCityId(Double rating,Integer cityId);

    public List<Apartment> findAllByRatingGreaterThanAndCityId(Double rating,Integer cityId);

    @Query("SELECT d.apartment FROM Distance d WHERE d.college.id =: collegeId AND d.college.city.id = d.apartment.city.id")
    public List<Apartment> findApartmentsByCollegeAndSameCity(Integer collegeId);

    @Query("SELECT d.apartment FROM Distance d WHERE d.college.id = :collegeId AND d.distanceValue < :distanceValue")
    public List<Apartment> findApartmentsByCollegeAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue);

    @Query("SELECT d.apartment FROM Distance d WHERE d.college.id = :collegeId AND d.college.city.id = d.apartment.city.id AND d.distanceValue < :distanceValue")
    public List<Apartment> findApartmentsByCollegeAndCityAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue);
    @Transactional
    public void deleteById(Integer id);
   /* @Query("select * from apartment ap where")
    public List<Apartment> findBydistance()*/
}
