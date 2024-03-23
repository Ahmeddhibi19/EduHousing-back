package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.RentalDetailsApi;
import com.PFA2.EduHousing.dto.RentalDetailsdto;
import com.PFA2.EduHousing.services.RentalDetailsService.RentalDetailsService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class RentalDetailsController implements RentalDetailsApi {
    private final RentalDetailsService rentalDetailsService;

    public RentalDetailsController(RentalDetailsService rentalDetailsService){
        this.rentalDetailsService=rentalDetailsService;
    }

    @Override
    public RentalDetailsdto save(RentalDetailsdto rentalDetailsdto, Integer apartmentId) {
        return rentalDetailsService.save(rentalDetailsdto,apartmentId);
    }

    @Override
    public RentalDetailsdto findById(Integer id) {
        return rentalDetailsService.findById(id);
    }

    @Override
    public List<RentalDetailsdto> findAllByApartmentId(Integer apartmentId) {
        return rentalDetailsService.findAllByApartmentId(apartmentId);
    }

    @Override
    public List<RentalDetailsdto> findRentalByPriceAndIsCurrent(Double val) {
        return rentalDetailsService.findRentalByPriceAndIsCurrent(val);
    }

    @Override
    public List<RentalDetailsdto> findRentalByStartDate(Date val) {
        return rentalDetailsService.findRentalByStartDate(val);
    }

    @Override
    public List<RentalDetailsdto> getRentalHistoryByApartment(Integer apartmentId) {
        return rentalDetailsService.getRentalHistoryByApartment(apartmentId);
    }

    @Override
    public List<RentalDetailsdto> getRentalDetailsByPeriod(Date startDate, Date endDate) {
        return rentalDetailsService.getRentalDetailsByPeriod(startDate,endDate);
    }

    @Override
    public List<RentalDetailsdto> getRentalDetailsByStudentCollegeCity(Integer studentCollegeCityId) {
        return rentalDetailsService.getRentalDetailsByStudentCollegeCity(studentCollegeCityId);
    }

    @Override
    public List<RentalDetailsdto> getRentalHistoryByStudent(Integer studentId) {
        return rentalDetailsService.getRentalHistoryByStudent(studentId);
    }

    @Override
    public RentalDetailsdto update(RentalDetailsdto rentalDetailsdto) {
        return rentalDetailsService.update(rentalDetailsdto);
    }

    @Override
    public void deleteById(Integer id) {
        rentalDetailsService.deleteById(id);
    }
}
