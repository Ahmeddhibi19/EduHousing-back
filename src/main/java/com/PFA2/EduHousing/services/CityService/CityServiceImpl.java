package com.PFA2.EduHousing.services.CityService;

import com.PFA2.EduHousing.dto.Citydto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.City;
import com.PFA2.EduHousing.model.College;
import com.PFA2.EduHousing.repository.jpa.CityRepository;
import com.PFA2.EduHousing.repository.jpa.CollegeRepository;
import com.PFA2.EduHousing.validator.CityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CityServiceImpl implements  CityService {

    private final CityRepository cityRepository;
    private CollegeRepository collegeRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository,CollegeRepository collegeRepository){
        this.cityRepository=cityRepository;
        this.collegeRepository=collegeRepository;
    }
    @Override
    public Citydto save(Citydto citydto) {
        List<String> errors=CityValidator.validate(citydto);
        if(!errors.isEmpty()){
            log.error("City is not valid {}",citydto);
            throw new InvalidEntityException("City is not valid ", ErrorCodes.CITY_NOT_VALID,errors);
        }

        Optional<City> cityName = cityRepository.findCitByName(citydto.getName());
        if(cityName.isPresent()){
            log.error("city all ready exists {}",citydto);
            throw new InvalidEntityException("City with this name : "+citydto.getName() +" all ready exists !!",
                    ErrorCodes.CITY_ALL_READY_EXISTS
            );
        }
        Optional<City> cityPostalCode = cityRepository.findCityByPostalCode(citydto.getPostalCode());
        if(cityPostalCode.isPresent()){
            log.error("city all ready exists {}",citydto);
            throw new InvalidEntityException("City with this postal code : "+citydto.getPostalCode() +" all ready exists !!",
                    ErrorCodes.CITY_ALL_READY_EXISTS
            );
        }

        return Citydto.fromEntity(
                cityRepository.save(
                        Citydto.toEntity(citydto)
                )
        );
    }

    @Override
    public Citydto findById(Integer id) {

        if(id== null){
            log.error("City id is null !!!");
            return null;
        }
        Optional<City> city= cityRepository.findById(id);
        return Optional.of(Citydto.fromEntity(city.get())).orElseThrow(
                ()->new EntityNotFoundException(
                        "City with this id :"+id+" is not found",
                        ErrorCodes.CITY_NOT_FOUND
                )
        );
    }

    @Override
    public Citydto findByName(String name) {
        if(!StringUtils.hasLength(name)){
            log.error("City name is null !!!");
            return null;
        }
        Optional<City> city= cityRepository.findCitByName(name);
        return Optional.of(Citydto.fromEntity(city.get())).orElseThrow(
                ()->new EntityNotFoundException(
                        "City with this name :"+name+" is not found",
                        ErrorCodes.CITY_NOT_FOUND
                )
        );
    }

    @Override
    public Citydto findByPostalCode(String postalCode) {
        if(!StringUtils.hasLength(postalCode)){
            log.error("City postal code is null !!!");
            return null;
        }
        Optional<City> city= cityRepository.findCitByName(postalCode);
        return Optional.of(Citydto.fromEntity(city.get())).orElseThrow(
                ()->new EntityNotFoundException(
                        "City with this postal code :"+postalCode+" is not found",
                        ErrorCodes.CITY_NOT_FOUND
                )
        );
    }
    @Override
    public List<Citydto> findAll(){
        return cityRepository.findAll().stream()
                .map(Citydto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Citydto update(Citydto citydto) {
        if(citydto.getId()==null){
            throw new IllegalArgumentException("City ID cannot be null");
        }
        City existingCity= cityRepository.findById(citydto.getId()).
                orElseThrow(
                        ()->new EntityNotFoundException(
                                "City with this id :"+citydto.getId()+" is not found",
                                ErrorCodes.CITY_NOT_FOUND
                        )
                );
        existingCity.setName(
                StringUtils.hasLength(citydto.getName())?
                        citydto.getName() : existingCity.getName()
        );

        existingCity.setPostalCode(
                StringUtils.hasLength(citydto.getPostalCode())?
                        citydto.getPostalCode() : existingCity.getPostalCode()
        );
        City updatedCity= cityRepository.save(existingCity);
        return Citydto.fromEntity(updatedCity);
    }

    @Override
    public void deleteById(Integer id) {
        if(id==null){
            log.error("city id is null !!");
            return;
        }
        cityRepository.deleteById(id);

    }

    @Override
    public Integer getCityByCollegeId(Integer id) {
        if(id==null){
            log.error("city id is null !!");
            return null;
        }
        Optional<College> college = collegeRepository.findById(id);
        return college.get().getCity().getId();
    }
}
