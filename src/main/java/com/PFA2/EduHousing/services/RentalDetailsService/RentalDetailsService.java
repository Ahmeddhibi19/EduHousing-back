package com.PFA2.EduHousing.services.RentalDetailsService;

import com.PFA2.EduHousing.dto.RentalDetailsdto;
import com.PFA2.EduHousing.model.RentalDetails;

import java.util.Date;
import java.util.List;

public interface RentalDetailsService {
    public RentalDetailsdto save(RentalDetailsdto rentalDetailsdto, Integer apartmentId);
    public RentalDetailsdto findById(Integer id);
    public List<RentalDetailsdto> findAllByApartmentId(Integer apartmentId);
    public List<RentalDetailsdto> findRentalByPriceAndIsCurrent(Double val);
    public List<RentalDetailsdto> findRentalByStartDate(Date val);
    public List<RentalDetailsdto> getRentalHistoryByApartment(Integer apartmentId);
    public List<RentalDetailsdto> getRentalDetailsByPeriod(Date startDate,Date endDate);
    public List<RentalDetailsdto> getRentalDetailsByStudentCollegeCity(Integer studentCollegeCityId);
    public List<RentalDetailsdto> getRentalHistoryByStudent(Integer studentId);
    public RentalDetailsdto update(RentalDetailsdto rentalDetailsdto);
    public void deleteById(Integer id);
}
