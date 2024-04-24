package com.PFA2.EduHousing.services.ApartmentService;

import com.PFA2.EduHousing.dto.Apartmentdto;
import com.PFA2.EduHousing.dto.Citydto;
import com.PFA2.EduHousing.dto.Homeownerdto;
import com.PFA2.EduHousing.model.City;
import com.PFA2.EduHousing.model.Homeowner;
import com.PFA2.EduHousing.repository.jpa.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApartmentServiceImplTest {


    /*private ApartmentRepository apartmentRepository;


    private HomeownerRepository homeownerRepository;


    private DistanceRepository distanceRepository;


    private CityRepository cityRepository;


    private CollegeRepository collegeRepository;


    private ApartmentServiceImpl apartmentService;

    @Autowired
   public ApartmentServiceImplTest(
            ApartmentRepository apartmentRepository,
            HomeownerRepository homeownerRepository,
            DistanceRepository distanceRepository,
            CityRepository cityRepository,
            CollegeRepository collegeRepository,
            ApartmentServiceImpl apartmentService

    ){
        this.apartmentRepository=apartmentRepository;
        this.apartmentService=apartmentService;
        this.cityRepository=cityRepository;
        this.collegeRepository=collegeRepository;
        this.distanceRepository=distanceRepository;
        this.homeownerRepository=homeownerRepository;
   }

    @Test
    void save() {
        Integer homeownerId = 45;
        Integer cityId = 1;

        Homeowner homeowner = homeownerRepository.findById(homeownerId).orElseThrow();
        City city = cityRepository.findById(cityId).orElseThrow();
        Apartmentdto apartmentdto =Apartmentdto.builder()
                .id(2)
                .latitude(BigDecimal.valueOf(10.00))
                .longitude(BigDecimal.valueOf(20.0))
                .type("Type")
                .address("Address")
                .description("Description")
                .isRented(false)
                .rating(0.0)
                .homeowner(Homeownerdto.builder()
                        .firstName(homeowner.getFirstName())
                        .lastName(homeowner.getLastName())
                        .id(homeownerId)
                        .build())
                .citydto(Citydto.builder()
                        .id(cityId)
                        .postalCode(city.getPostalCode())
                        .name(city.getName())
                        .build())
                .build();
        Apartmentdto WillSavedApartmentdto =Apartmentdto.builder()

                .latitude(BigDecimal.valueOf(10.00))
                .longitude(BigDecimal.valueOf(20.0))
                .type("Type")
                .address("Address")
                .description("Description")
                .build();


        *//*when(homeownerRepository.findById(homeownerId)).thenReturn(Optional.of(homeowner));
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
        when(collegeRepository.findAll()).thenReturn(new ArrayList<>());*//*

        // Act
        Apartmentdto savedApartment = apartmentService.save(WillSavedApartmentdto, homeownerId, cityId);

        // Assert
        assertNotNull(savedApartment);
        assertEquals(apartmentdto.getLatitude(), savedApartment.getLatitude());
        assertEquals(apartmentdto.getLongitude(), savedApartment.getLongitude());
        assertEquals(apartmentdto.getType(), savedApartment.getType());
        assertEquals(apartmentdto.getAddress(), savedApartment.getAddress());
        assertEquals(apartmentdto.getDescription(), savedApartment.getDescription());
    }

    @Test
    void findById() {
        Integer homeownerId = 45;
        Integer cityId = 1;

        Homeowner homeowner = homeownerRepository.findById(homeownerId).orElseThrow();
        City city = cityRepository.findById(cityId).orElseThrow();
        Apartmentdto expectedApartment=Apartmentdto.builder()
                .id(2)
                .latitude(BigDecimal.valueOf(10.00))
                .longitude(BigDecimal.valueOf(20.0))
                .type("Type")
                .address("Address")
                .description("Description")
                .isRented(false)
                .rating(0.0)
                .homeowner(Homeownerdto.builder()
                        .firstName(homeowner.getFirstName())
                        .lastName(homeowner.getLastName())
                        .id(homeownerId)
                        .build())
                .citydto(Citydto.builder()
                        .id(cityId)
                        .postalCode(city.getPostalCode())
                        .name(city.getName())
                        .build())
                .build();
        Apartmentdto returnedApartment=apartmentService.findById(2);
        assertNotNull(returnedApartment);
        assertEquals(returnedApartment.getLatitude(), expectedApartment.getLatitude());
        assertEquals(returnedApartment.getLongitude(), expectedApartment.getLongitude());
        assertEquals(returnedApartment.getType(), expectedApartment.getType());
        assertEquals(returnedApartment.getAddress(), expectedApartment.getAddress());
        assertEquals(returnedApartment.getDescription(), expectedApartment.getDescription());



    }

    @Test
    void findByCityId() {
    }

    @Test
    void findByHomeownerId() {
    }

    @Test
    void findAllByType() {
    }

    @Test
    void findAllByCityIdAndHomeownerId() {
    }

    @Test
    void findAllByCityIdAndType() {
    }

    @Test
    void findAllByHomeownerIdAndType() {
    }

    @Test
    void findAllByHomeownerIdAndIsRented() {
    }

    @Test
    void findAllByCityIdNotIsRented() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void findApartmentByRatingLessThanAndCityId() {
    }

    @Test
    void findApartmentByRatingGreaterThanAndCityId() {
    }

    @Test
    void findApartmentsByCollegeAndSameCity() {
    }

    @Test
    void findApartmentsByCollegeAndDistanceLessThan() {
    }

    @Test
    void findApartmentsByCollegeAndCityAndDistanceLessThan() {
    }

    @Test
    void findAll() {
    }*/
}