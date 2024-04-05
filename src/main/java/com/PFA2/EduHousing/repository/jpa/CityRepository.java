package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {

    public Optional<City> findCitByName(String name);

    public Optional<City> findCityByPostalCode(String postalCode);
}
