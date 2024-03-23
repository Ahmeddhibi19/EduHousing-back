package com.PFA2.EduHousing.services.ApartmentImageService;

import com.PFA2.EduHousing.Utils.ImageUtils;
import com.PFA2.EduHousing.dto.ApartmentImagedto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.ApartmentImage;
import com.PFA2.EduHousing.repository.ApartmentImageRepository;
import com.PFA2.EduHousing.repository.ApartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApartmentImageServiceImpl implements ApartmentImageService {
    private final ApartmentImageRepository apartmentImageRepository;
    private final ApartmentRepository apartmentRepository;
    @Autowired
    public ApartmentImageServiceImpl(
            ApartmentImageRepository apartmentImageRepository,
            ApartmentRepository apartmentRepository
    ){
        this.apartmentImageRepository=apartmentImageRepository;
        this.apartmentRepository=apartmentRepository;
    }
    @Override
    public String save(MultipartFile file, Integer apartmentId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        Apartment apartment=apartmentRepository.findById(apartmentId).orElseThrow(
                ()->new EntityNotFoundException("there is no apartment with this id :"+apartmentId,
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
        byte[] data = file.getBytes();
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();

        /*ApartmentImage apartmentImage = new ApartmentImage();
        apartmentImage.setName(fileName);
        apartmentImage.setType(fileType);
        apartmentImage.setData(data);
        apartmentImage.setApartment(apartment);*/
        ApartmentImage apartmentImage=ApartmentImage.builder()
                .name(fileName)
                .type(fileType)
                .data(ImageUtils.compressImage(file.getBytes()))
                .apartment(apartment)
                .build();

        apartment.getImageList().add(apartmentImage);

        if (apartmentImage==null){
            return "file failed to upload"+fileName;
        }
        apartmentRepository.save(apartment);
        apartmentImageRepository.save(apartmentImage);

        return "file uploaded successfully"+fileName;

    }

    @Override
    public byte[] findById(Integer id) {
        if (id==null){
            log.error("apartment image id is null !!!");
            return null;
        }
        Optional<ApartmentImage> apartmentImage=apartmentImageRepository.findById(id);
        if (apartmentImage.isEmpty()){
            throw new EntityNotFoundException("No apartment image with this id :"+id,
                    ErrorCodes.HOME_IMAGE_NOT_FOUND);
        }


        return ImageUtils.decompressImage(apartmentImage.get().getData());
    }

    @Override
    public List<byte[]> findAllByApartmentId(Integer id) {
        if(id==null){
            log.error("Apartment id is null !!!");
            return null;
        }
        Apartment apartment=apartmentRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("there is no apartment with this id :"+id,
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
        List<byte[]> images=new ArrayList<>();
        List<ApartmentImage> apartmentImageList=apartmentImageRepository.findAllByApartmentId(id);
        for (ApartmentImage apartmentImage:apartmentImageList){
            byte[] image=ImageUtils.decompressImage(apartmentImage.getData());
            images.add(image);
        }

        return images;
    }

    @Override
    public List<byte[]> findAll() {
        List<ApartmentImage> apartmentImageList=apartmentImageRepository.findAll();
        List<byte[]> images=new ArrayList<>();
        for (ApartmentImage apartmentImage:apartmentImageList){
            images.add(ImageUtils.decompressImage(apartmentImage.getData()));
        }
        return images;
    }

    @Override
    public void deleteById(Integer id) {
        if(id==null){
            log.error("deleting apartment image with null id ");
            return;
        }
        apartmentImageRepository.deleteById(id);
    }
}
