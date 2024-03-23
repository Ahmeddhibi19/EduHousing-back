package com.PFA2.EduHousing.services.ProfileImageService;

import com.PFA2.EduHousing.dto.ProfileImagedto;
import com.PFA2.EduHousing.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileImageService {
    public String save(MultipartFile file, Integer userId)throws IOException;
    public byte[] findById(Integer id);
    public byte[] findByUserId(Integer userId);
    public void deleteById(Integer id);
}
