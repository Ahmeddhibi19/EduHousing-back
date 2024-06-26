package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.ApartmentImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentImageRepository extends JpaRepository<ApartmentImage,Integer> {

    public List<ApartmentImage> findAllByApartmentId(Integer id);
    @Transactional
    public void deleteById(Integer id);
}
