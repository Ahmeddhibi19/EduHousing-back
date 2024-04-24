package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.ProfileImageApi;
import com.PFA2.EduHousing.services.ProfileImageService.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProfileImageController implements ProfileImageApi {
    private final ProfileImageService profileImageService;
    @Autowired
    public ProfileImageController(ProfileImageService profileImageService){
        this.profileImageService=profileImageService;
    }
    @Override
    public String save(MultipartFile file, Integer userId) throws IOException {
        return profileImageService.save(file,userId);
    }

    @Override
    public byte[] findById(Integer id) {
        return profileImageService.findById(id);
    }

    @Override
    public byte[] findByUserId(Integer userId) {
        return profileImageService.findByUserId(userId);
    }

    @Override
    public void deleteById(Integer id) {
        profileImageService.deleteById(id);
    }

    @Override
    public void deleteByUserId(Integer id) {
        profileImageService.deleteByUserId(id);
    }

    @Override
    public byte[] findByUserEmail(String email) {
        return profileImageService.findByUserEmail(email);
    }
}
