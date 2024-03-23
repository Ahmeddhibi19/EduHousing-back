package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.ApartmentImageApi;
import com.PFA2.EduHousing.dto.ApartmentImagedto;
import com.PFA2.EduHousing.services.ApartmentImageService.ApartmentImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ApartmentImageController implements ApartmentImageApi {
    private final ApartmentImageService apartmentImageService;

    @Autowired
    public ApartmentImageController(ApartmentImageService apartmentImageService){
        this.apartmentImageService=apartmentImageService;
    }
    @Override
    public String save(MultipartFile file, Integer apartmentId) throws IOException {
        return apartmentImageService.save(file,apartmentId) ;
    }

    @Override
    public byte[] findById(Integer id) {
        return apartmentImageService.findById(id);
    }

    @Override
    public List<byte[]> findAllByApartmentId(Integer id) {
        return apartmentImageService.findAllByApartmentId(id);
    }

    @Override
    public List<byte[]> findAll() {
        return apartmentImageService.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        apartmentImageService.deleteById(id);
    }
}
