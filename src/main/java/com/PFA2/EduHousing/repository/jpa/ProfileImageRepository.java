package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage,Integer> {

    public Optional<ProfileImage> findProfileImageByUserId(Integer userId);
    public void deleteProfileImageByUserId(Integer userId);
}
