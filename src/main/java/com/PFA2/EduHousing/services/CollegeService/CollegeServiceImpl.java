package com.PFA2.EduHousing.services.CollegeService;

import com.PFA2.EduHousing.dto.Collegedto;
import com.PFA2.EduHousing.dto.Distancedto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.City;
import com.PFA2.EduHousing.model.College;
import com.PFA2.EduHousing.model.Distance;
import com.PFA2.EduHousing.repository.ApartmentRepository;
import com.PFA2.EduHousing.repository.CityRepository;
import com.PFA2.EduHousing.repository.CollegeRepository;
import com.PFA2.EduHousing.repository.DistanceRepository;
import com.PFA2.EduHousing.validator.CollegeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;
    private final DistanceRepository distanceRepository;
    private final CityRepository cityRepository;
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public CollegeServiceImpl(
            CollegeRepository collegeRepository,
            DistanceRepository distanceRepository,
            ApartmentRepository apartmentRepository,
            CityRepository cityRepository
    ){
        this.collegeRepository=collegeRepository;
        this.distanceRepository=distanceRepository;
        this.apartmentRepository=apartmentRepository;
        this.cityRepository=cityRepository;
    }

    @Override
    public Collegedto save(Collegedto collegedto,Integer cityId) {

        List<String> errors = CollegeValidator.validate(collegedto);
        if(!errors.isEmpty()){
            log.error("College is invalid {}",collegedto);
            throw new InvalidEntityException("College is Invalid", ErrorCodes.COLLEGE_NOT_VALID,errors);
        }
        Optional<College> collegeName=collegeRepository.findCollegeByName(collegedto.getName());
        if(collegeName.isPresent()){
            log.error("College name all ready exists {}",collegedto);
            throw new InvalidEntityException("college with this name :"+collegedto.getName()+" all ready exists",ErrorCodes.COLLEGE_ALL_READY_EXISTS);
        }
        City city=cityRepository.findById(cityId).orElseThrow(
                ()->new EntityNotFoundException("City not found",ErrorCodes.CITY_NOT_FOUND)
        );

        List<Apartment> apartmentList=apartmentRepository.findAll().stream().toList();
        College college= Collegedto.toEntity(collegedto);
        college.setCity(city);
        city.getCollegeList().add(college);
        cityRepository.save(city);
        if (!apartmentList.isEmpty()){
            for (Apartment apartment:apartmentList){
                BigDecimal distance=Distancedto.calculateDistance(
                        collegedto.getLatitude(),collegedto.getLongitude(),
                        apartment.getLatitude(),apartment.getLongitude()
                );
                Distance dis=new Distance();
                dis.setDistanceValue(distance);
                dis.setCollege(college);
                college.getDistanceList().add(dis);
                dis.setApartment(apartment);
                apartment.getDistanceList().add(dis);
                apartmentRepository.save(apartment);
                distanceRepository.save(dis);

            };
        }

        return Collegedto.fromEntity(collegeRepository.save(college));
    }

    @Override
    public Collegedto findById(Integer id) {
        if(id==null){
            log.error("college id is null");
            return null;
        }
         Optional<College> college = collegeRepository.findById(id);
        return Optional.of(Collegedto.fromEntity(college.get())).orElseThrow(
                ()->new EntityNotFoundException("College with this id :"+id+"is not found",ErrorCodes.COLLEGE_NOT_FOUND)
        );
    }

    @Override
    public Collegedto findByName(String name) {
        if(!StringUtils.hasLength(name)){
            log.error("college id is null");
            return null;
        }
        Optional<College> college = collegeRepository.findCollegeByName(name);
        return Optional.of(Collegedto.fromEntity(college.get())).orElseThrow(
                ()->new EntityNotFoundException("College with this name :"+name+"is not found",ErrorCodes.COLLEGE_NOT_FOUND)
        );
    }

    @Override
    public List<Collegedto> findAll() {
        return collegeRepository.findAll().stream()
                .map(Collegedto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Collegedto> findByCityId(Integer id) {
        return collegeRepository.findAllByCityId(id).stream()
                .map(Collegedto::fromEntity)
                .collect(Collectors.toList());
    }
    //only name
    @Override
    public Collegedto update(Collegedto collegedto) {

        if(collegedto.getId()==null){
            throw new IllegalArgumentException("College ID cannot be null");
        }
        College existingCollege=collegeRepository.findById(collegedto.getId()).orElseThrow(
                ()->new EntityNotFoundException("college with this id :"+collegedto.getId()+" is not found",
                    ErrorCodes.COLLEGE_NOT_FOUND
                        )
        );
        existingCollege.setName(
                StringUtils.hasLength(collegedto.getName())?
                        collegedto.getName() : existingCollege.getName()
        );
        College updatedCollege= collegeRepository.save(existingCollege);
        return Collegedto.fromEntity(updatedCollege);
    }

    @Override
    public void deleteById(Integer id) {
        if(id==null){
            log.error("college id is null");
            return;
        }
        collegeRepository.deleteById(id);
    }

    @Override
    public List<Collegedto> findCollegesByApartmentAndSameCity(Integer apartmentId) {
        if(apartmentId==null){
            log.error("apartment id is null !!");
            return null;
        }
        Apartment apartment=apartmentRepository.findById(apartmentId).orElseThrow(
                ()-> new EntityNotFoundException("no apartment with this id :"+apartmentId,ErrorCodes.APARTMENT_NOT_FOUND)
        );
        return collegeRepository.findCollegesByApartmentAndSameCity(apartmentId).stream()
                .map(Collegedto::fromEntity)
                .collect(Collectors.toList());
    }

  /*  @Override
    public List<Collegedto> findCollegesByApartmentAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue) {
        return null;
    }

    @Override
    public List<Collegedto> findCollegesByApartmentAndCityAndDistanceLessThan(Integer apartmentId, BigDecimal distanceValue) {
        return null;
    }*/
}
