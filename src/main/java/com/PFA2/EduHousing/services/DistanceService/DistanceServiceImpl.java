package com.PFA2.EduHousing.services.DistanceService;

import com.PFA2.EduHousing.dto.Distancedto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.City;
import com.PFA2.EduHousing.model.College;
import com.PFA2.EduHousing.model.Distance;
import com.PFA2.EduHousing.repository.jpa.ApartmentRepository;
import com.PFA2.EduHousing.repository.jpa.CityRepository;
import com.PFA2.EduHousing.repository.jpa.CollegeRepository;
import com.PFA2.EduHousing.repository.jpa.DistanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DistanceServiceImpl implements DistanceService {
    private final DistanceRepository distanceRepository;
    private final ApartmentRepository apartmentRepository;
    private final CollegeRepository collegeRepository;
    private final CityRepository cityRepository;

    @Autowired
    public DistanceServiceImpl(
            DistanceRepository distanceRepository,
            ApartmentRepository apartmentRepository,
            CollegeRepository collegeRepository,
            CityRepository cityRepository
    ){
        this.distanceRepository=distanceRepository;
        this.apartmentRepository=apartmentRepository;
        this.collegeRepository=collegeRepository;
        this.cityRepository=cityRepository;
    }

    @Override
    public Distancedto findByid(Integer id) {
        if(id==null){
            log.error("Distance id is null");
            return null;
        }
        Optional<Distance> distance=distanceRepository.findById(id);
        return Optional.of(Distancedto.fromEntity(distance.get())).orElseThrow(
                ()-> new EntityNotFoundException("No distance with this id :"+id+" is found",
                        ErrorCodes.Distance_NOT_FOUND)
        ) ;
    }

    @Override
    public List<Distancedto> findAll() {
        return distanceRepository.findAll().stream()
                .map(Distancedto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Distancedto> findAllByApartmentId(Integer apartmentId) {
        if(apartmentId==null){
            log.error("apartment id is null");
            throw new IllegalArgumentException("apartment id is null");
        }
        Apartment apartment = apartmentRepository.findById(apartmentId).orElseThrow(
                ()->new EntityNotFoundException("no apartment with this id :"+apartmentId+" is found",
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );


        return distanceRepository.findAllByApartmentId(apartmentId).stream()
                .map(Distancedto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Distancedto> findAllByCollegeId(Integer collegeId) {
        if(collegeId==null){
            log.error("apartment id is null");
            throw new IllegalArgumentException("apartment id is null");

        }
        College college = collegeRepository.findById(collegeId).orElseThrow(
                ()-> new EntityNotFoundException(
                        "no college with this id :"+collegeId+" is found",
                        ErrorCodes.COLLEGE_NOT_FOUND
                )
        );
        return distanceRepository.findAllByCollegeId(collegeId).stream()
                .map(Distancedto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Distancedto> findAllByInSameCity(Integer cityId) {
        if(cityId==null){
            log.error("city Id is null");
            return null;
        }
        City city=cityRepository.findById(cityId).orElseThrow(
                ()->new EntityNotFoundException("no city with this Id :"+cityId,ErrorCodes.CITY_NOT_FOUND)
        );
        return distanceRepository.findAllInSameCity(cityId).stream()
                .map(Distancedto::fromEntity)
                .collect(Collectors.toList());
    }

   /* @Override
    public List<Apartmentdto> findApartmentsByCollegeAndSameCity(Integer collegeId) {
        return null;
    }

    @Override
    public List<Collegedto> findCollegesByApartmentAndSameCity(Integer apartmentId) {
        return null;
    }

    @Override
    public List<Apartmentdto> findApartmentsByCollegeAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue) {
        if(collegeId==null){
            log.error("college id is null");
            throw new IllegalArgumentException("college id is null");
        }
        College college = collegeRepository.findById(collegeId).orElseThrow(
                ()-> new EntityNotFoundException(
                        "no college with this id :"+collegeId+" is found",
                        ErrorCodes.COLLEGE_NOT_FOUND
                )
        );
        return distanceRepository.findApartmentsByCollegeAndDistanceLessThan(college.getId(),distanceValue).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Collegedto> findCollegesByApartmentAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue) {
        return null;
    }

    @Override
    public List<Apartmentdto> findApartmentsByCollegeAndCityAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue) {
        return null;
    }

    @Override
    public List<Collegedto> findCollegesByApartmentAndCityAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue) {
        return null;
    }
*/

}
