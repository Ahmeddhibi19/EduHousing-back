package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage,Integer> {

    @Query("select p from ProfileImage p where p.user.id=:userId")
    public Optional<ProfileImage> findProfileImageByUserId(Integer userId);
    @Query("DELETE FROM ProfileImage p WHERE p.user.id = :userId")
    public void deleteProfileImageByUserId(Integer userId);
    @Query("select p from  ProfileImage  p where p.user.email=:email")
    public Optional<ProfileImage> findProfileImageByUserEmail(String email);
}
