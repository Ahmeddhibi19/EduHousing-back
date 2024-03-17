package com.PFA2.EduHousing.repository;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.ApartmentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentImageRepository extends JpaRepository<ApartmentImage,Integer> {

    public List<ApartmentImage> findAllByApartmentId(Integer id);
}
