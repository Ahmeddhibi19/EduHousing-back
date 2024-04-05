package com.PFA2.EduHousing.services.ApartmentService;

import com.PFA2.EduHousing.dto.Apartmentdto;
import com.PFA2.EduHousing.dto.Distancedto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.*;
import com.PFA2.EduHousing.repository.jpa.*;
import com.PFA2.EduHousing.validator.ApartmentValidator;
import jakarta.transaction.Transactional;
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
public class ApartmentServiceImpl implements ApartmentService{
    private final ApartmentRepository apartmentRepository;
    private final HomeownerRepository homeownerRepository;
    private final DistanceRepository distanceRepository;
    private final CityRepository cityRepository;
    private final CollegeRepository collegeRepository;
    @Autowired
    public ApartmentServiceImpl(
            ApartmentRepository apartmentRepository,
            DistanceRepository distanceRepository,
            HomeownerRepository homeownerRepository,
            CityRepository cityRepository,
            CollegeRepository collegeRepository
    ){
        this.apartmentRepository=apartmentRepository;
        this.distanceRepository=distanceRepository;
        this.homeownerRepository=homeownerRepository;
        this.cityRepository=cityRepository;
        this.collegeRepository=collegeRepository;
    }
    @Override
    @Transactional
    public Apartmentdto save(Apartmentdto apartmentdto, Integer homeownerId,Integer cityId) {
        Homeowner homeowner=homeownerRepository.findById(homeownerId).orElseThrow(
                ()->new EntityNotFoundException("No Homeowner with the provided id :"+homeownerId+"is found",
                        ErrorCodes.HOMEOWNER_NOT_FOUND)
        );
        City city=cityRepository.findById(cityId).orElseThrow(
                ()->new EntityNotFoundException("No city with the provided id :"+cityId+"is found",
                        ErrorCodes.CITY_NOT_FOUND)
        );
        List<String> errors= ApartmentValidator.validate(apartmentdto);
        if(!errors.isEmpty()){
            log.error("invalid apartment {}",apartmentdto);
            throw new InvalidEntityException("Invalid apartment",ErrorCodes.APARTMENT_NOT_VALID,errors);
        }
        List<College> collegeList=collegeRepository.findAll().stream().toList();
        Apartment apartment=Apartmentdto.toEntity(apartmentdto);
        apartment.setCity(city);
        apartment.setHomeowner(homeowner);
        city.getApartmentList().add(apartment);
        apartment.setIsRented(false);
        //apartment.setCreationTime(Instant.now());
        homeowner.getApartmentList().add(apartment);
        if(!collegeList.isEmpty()){
            for (College college:collegeList){
                BigDecimal distance= Distancedto.calculateDistance(
                        college.getLatitude(),college.getLongitude(),
                        apartmentdto.getLatitude(),apartmentdto.getLongitude()
                );
                Distance dis=new Distance();
                dis.setDistanceValue(distance);
                dis.setApartment(apartment);
                apartment.getDistanceList().add(dis);
                dis.setCollege(college);
                college.getDistanceList().add(dis);
                collegeRepository.save(college);
                distanceRepository.save(dis);
            }
        }
        cityRepository.save(city);
        homeownerRepository.save(homeowner);

        return Apartmentdto.fromEntity(apartmentRepository.save(apartment));
    }

    @Override
    public Apartmentdto findById(Integer id) {
        if(id==null){
            log.error("apartment id is null");
            return null;
        }
        Optional<Apartment> apartment=apartmentRepository.findById(id);
        return Optional.of(Apartmentdto.fromEntity(apartment.get())).orElseThrow(
                ()-> new EntityNotFoundException("no apartment with this id :"+id+"is found",
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
    }

    @Override
    public List<Apartmentdto> findByCityId(Integer cityId) {
        if(cityId==null){
            log.error("city id is null");
            return null;
        }
        Optional<City> city =cityRepository.findById(cityId);
        if(city.isEmpty()){
            log.error("no city with this id");
            throw new EntityNotFoundException("no city with this id :"+cityId,ErrorCodes.CITY_NOT_FOUND);
        }
        return apartmentRepository.findAllByCityId(cityId).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findByHomeownerId(Integer homeownerId) {
        if(homeownerId==null){
            log.error("homeowner id is null");
            return null;
        }
        Optional<Homeowner> homeowner =homeownerRepository.findById(homeownerId);
        if(homeowner.isEmpty()){
            log.error("no homeowner with this id");
            throw new EntityNotFoundException("no apartment with this homeowner id :"+homeownerId,ErrorCodes.HOMEOWNER_NOT_FOUND);
        }
        return apartmentRepository.findAllByHomeownerId(homeownerId).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findAllByType(String type) {
        if(!StringUtils.hasLength(type)){
            log.error("no type provided");
            return null;
        }
        return apartmentRepository.findAllByType(type).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findAllByCityIdAndHomeownerId(Integer cityId, Integer homeownerId) {
        Optional<City> city =cityRepository.findById(cityId);
        if(city.isEmpty()){
            log.error("no city with this id");
            throw new EntityNotFoundException("no city with this id :"+cityId,ErrorCodes.CITY_NOT_FOUND);
        }
        Optional<Homeowner> homeowner =homeownerRepository.findById(homeownerId);
        if(homeowner.isEmpty()){
            log.error("no homeowner with this id");
            throw new EntityNotFoundException("no city with this id :"+homeownerId,ErrorCodes.HOMEOWNER_NOT_FOUND);
        }
        return apartmentRepository.findAllByCityIdAndHomeownerId(cityId,homeownerId).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findAllByCityIdAndType(Integer cityId, String type) {
        if(!StringUtils.hasLength(type)){
            log.error("no type provided");
            return null;
        }
        Optional<City> city =cityRepository.findById(cityId);
        if(city.isEmpty()){
            log.error("no city with this id");
            throw new EntityNotFoundException("no city with this id :"+cityId,ErrorCodes.CITY_NOT_FOUND);
        }
        return apartmentRepository.findAllByCityIdAndType(cityId,type).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findAllByHomeownerIdAndType(Integer homeownerId, String type) {
        if(!StringUtils.hasLength(type)){
            log.error("no type provided");
            return null;
        }
        Optional<Homeowner> homeowner =homeownerRepository.findById(homeownerId);
        if(homeowner.isEmpty()){
            log.error("no homeowner with this id");
            throw new EntityNotFoundException("no city with this id :"+homeownerId,ErrorCodes.HOMEOWNER_NOT_FOUND);
        }
        return apartmentRepository.findAllByHomeownerIdAndType(homeownerId,type).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findAllByHomeownerIdAndIsRented(Integer homeownerId) {
        return apartmentRepository.findAllByHomeownerIdAndIsRented(homeownerId).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findAllByCityIdNotIsRented(Integer cityId) {
        return apartmentRepository.findAllByCityIdNotIsRented(cityId).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void  deleteById(Integer id) {
        if(id==null){
            log.error("apartment id is null");
            return;
        }
        apartmentRepository.deleteById(id);
    }
    @Override
    public Apartmentdto update(Apartmentdto apartmentdto){
        if(apartmentdto.getId()==null){
            throw new IllegalArgumentException("cannot update apartment with null id");
        }
        Apartment existingApartment=apartmentRepository.findById(apartmentdto.getId()).orElseThrow(
                ()->new EntityNotFoundException("No apartment with the provided id :"+apartmentdto.getId(),
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
        existingApartment.setLatitude(
                apartmentdto.getLatitude()!=null?
                        apartmentdto.getLatitude():existingApartment.getLatitude()
        );

        existingApartment.setLongitude(
                apartmentdto.getLongitude()!=null ?
                        apartmentdto.getLongitude():existingApartment.getLongitude()
        );

        existingApartment.setType(
                StringUtils.hasLength(apartmentdto.getType())?
                        apartmentdto.getType() : existingApartment.getType()
        );

        existingApartment.setAddress(
                StringUtils.hasLength(apartmentdto.getAddress()) ?
                        apartmentdto.getAddress() : existingApartment.getAddress()
        );





        existingApartment.setDescription(
                StringUtils.hasLength(apartmentdto.getDescription())?
                        apartmentdto.getDescription() : existingApartment.getDescription()
        );

        Apartment updatedApartment=apartmentRepository.save(existingApartment);
        return Apartmentdto.fromEntity(updatedApartment);
    }
    @Override
    public List<Apartmentdto> findApartmentByRatingLessThanAndCityId(Double rating,Integer cityId){
        if(cityId==null){
            log.error("null city id !!!");
            return null;
        }
        Optional<City> city =cityRepository.findById(cityId);
        if(city.isEmpty()){
            log.error("no city with this id");
            throw new EntityNotFoundException("no city with this id :"+cityId,ErrorCodes.CITY_NOT_FOUND);
        }
        if(rating==null){
            log.error("null rating !!!");
            return null;
        }
        if(rating<0){
            log.error("negative rating !!!");
            return null;
        }
        return apartmentRepository.findAllByRatingLessThanAndCityId(rating,cityId).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public List<Apartmentdto> findApartmentByRatingGreaterThanAndCityId(Double rating,Integer cityId){
        if(cityId==null){
            log.error("null city id !!!");
            return null;
        }
        Optional<City> city =cityRepository.findById(cityId);
        if(city.isEmpty()){
            log.error("no city with this id");
            throw new EntityNotFoundException("no city with this id :"+cityId,ErrorCodes.CITY_NOT_FOUND);
        }
        if(rating==null){
            log.error("null rating !!!");
            return null;
        }
        if(rating>10){
            log.error("rating greater than 10 !!!");
            return null;
        }
        return apartmentRepository.findAllByRatingGreaterThanAndCityId(rating,cityId).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findApartmentsByCollegeAndSameCity(Integer collegeId) {
        if(collegeId==null){
            log.error("null college id");
            return null;
        }
        College college=collegeRepository.findById(collegeId).orElseThrow(
                ()->new EntityNotFoundException("no college with this id :"+collegeId,ErrorCodes.COLLEGE_NOT_FOUND)
        );

        return apartmentRepository.findApartmentsByCollegeAndSameCity(collegeId).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findApartmentsByCollegeAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue) {
        if(collegeId==null){
            log.error("null college id");
            return null;
        }
        College college=collegeRepository.findById(collegeId).orElseThrow(
                ()->new EntityNotFoundException("no college with this id :"+collegeId,ErrorCodes.COLLEGE_NOT_FOUND)
        );



        return apartmentRepository.findApartmentsByCollegeAndDistanceLessThan(collegeId,distanceValue).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartmentdto> findApartmentsByCollegeAndCityAndDistanceLessThan(Integer collegeId, BigDecimal distanceValue) {
        if(collegeId==null){
            log.error("null college id");
            return null;
        }
        College college=collegeRepository.findById(collegeId).orElseThrow(
                ()->new EntityNotFoundException("no college with this id :"+collegeId,ErrorCodes.COLLEGE_NOT_FOUND)
        );
        return apartmentRepository.findApartmentsByCollegeAndCityAndDistanceLessThan(collegeId,distanceValue).stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public List<Apartmentdto> findAll(){
        return apartmentRepository.findAll().stream()
                .map(Apartmentdto::fromEntity)
                .collect(Collectors.toList());
    }

}
