package com.PFA2.EduHousing.repository;

import com.PFA2.EduHousing.model.RentalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RentalDetailsRepository extends JpaRepository<RentalDetails,Integer> {


    public List<RentalDetails> findAllByApartmentId(Integer apartmentId);

    @Query("select r from RentalDetails r where r.monthlyAmount <= :val and r.isCurrent=true")
    public List<RentalDetails> findRentalByPriceAndIsCurrent(Double val);

    @Query("select r from RentalDetails r where r.startDate >=:val and r.isCurrent=true ")
    public List<RentalDetails> findRentalByStartDate(Date val);

    @Query("select rd from RentalDetails  rd join rd.requestSet r where rd.isCurrent=false And rd.apartment.id=:apartmentId and r.status='VALIDATED'")
    public List<RentalDetails> getRentalHistoryByApartment(Integer apartmentId);

    @Query("SELECT rd FROM RentalDetails rd JOIN rd.requestSet r WHERE r.status = 'VALIDATED' and r.student.id=:studentId and rd.apartment.id = :apartmentId")
    public List<RentalDetails> getRentalHistoryByStudentAndApartment(Integer studentId,Integer apartmentId);

    @Query("select r from  RentalDetails r where r.startDate>=:startDate and r.endDate<=:endDate")
    public List<RentalDetails> getRentalDetailsByPeriod(Date startDate,Date endDate);

    @Query("select r from RentalDetails r where r.apartment.city.id=:studentCollegeCityId and r.isCurrent=true")
    public List<RentalDetails> getRentalDetailsByStudentCollegeCity(Integer studentCollegeCityId);

    @Query("SELECT rd FROM RentalDetails rd JOIN rd.requestSet r WHERE r.status = 'VALIDATED' and r.student.id=:studentId")
    public List<RentalDetails> getRentalHistoryByStudent(Integer studentId);

    void deleteById(Integer id);
}
