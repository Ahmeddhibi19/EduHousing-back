package com.PFA2.EduHousing.repository;

import com.PFA2.EduHousing.model.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites,Integer> {

    public List<Favourites> findAllByStudentId(Integer studentId);
}
