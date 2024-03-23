package com.PFA2.EduHousing.services.ApartmentImageService;

import com.PFA2.EduHousing.dto.ApartmentImagedto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ApartmentImageService {

    public String save(MultipartFile file, Integer apartmentId)throws IOException;

    public byte[] findById(Integer id);

    public List<byte[]> findAllByApartmentId(Integer id);
    public List<byte[]> findAll();
    public void deleteById(Integer id);

}