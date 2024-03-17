package com.PFA2.EduHousing.services.ApartmentImageService;

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
    public ApartmentImagedto save(ApartmentImagedto apartmentImagedto,Integer apartmentId) {
        if(apartmentImagedto==null){
            log.error("saving empty image ");
            throw new InvalidEntityException("saving empty Apartment image",ErrorCodes.ADMIN_NOT_VALID);
        }
        Apartment apartment=apartmentRepository.findById(apartmentId).orElseThrow(
                ()->new EntityNotFoundException("there is no apartment with this id :"+apartmentId,
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
        ApartmentImage apartmentImage=ApartmentImagedto.toEntity(apartmentImagedto);
        apartmentImage.setApartment(apartment);
        apartment.getImageList().add(apartmentImage);
        apartmentRepository.save(apartment);

        return ApartmentImagedto.fromEntity(
                apartmentImageRepository.save(apartmentImage)
        );
    }

    @Override
    public ApartmentImagedto findById(Integer id) {
        if (id==null){
            log.error("apartmeny image id is null !!!");
            return null;
        }
        Optional<ApartmentImage> apartmentImage=apartmentImageRepository.findById(id);
        return Optional.of(ApartmentImagedto.fromEntity(apartmentImage.get())).orElseThrow(
                ()->new EntityNotFoundException("No apartment image with this id :"+id,
                        ErrorCodes.HOME_IMAGE_NOT_FOUND)
        );
    }

    @Override
    public List<ApartmentImagedto> findAllByApartmentId(Integer id) {
        if(id==null){
            log.error("Apartment id is null !!!");
            return null;
        }
        Apartment apartment=apartmentRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("there is no apartment with this id :"+id,
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
        return apartmentImageRepository.findAllByApartmentId(id).stream()
                .map(ApartmentImagedto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentImagedto> findAll() {
        return  apartmentImageRepository.findAll().stream()
                .map(ApartmentImagedto::fromEntity)
                .collect(Collectors.toList());
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
