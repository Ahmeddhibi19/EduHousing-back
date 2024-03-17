package com.PFA2.EduHousing.services.ProfileImageService;

import com.PFA2.EduHousing.dto.ProfileImagedto;
import com.PFA2.EduHousing.model.User;

public interface ProfileImageService {
    public ProfileImagedto save(ProfileImagedto profileImagedto, Integer userId);
    public ProfileImagedto findById(Integer id);
    public ProfileImagedto findByUserId(Integer userId);
    public void deleteById(Integer id);
}
