package com.PFA2.EduHousing.repository;

import com.PFA2.EduHousing.model.RentalDetails;
import com.PFA2.EduHousing.model.RentalFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalFeedbackRepository extends JpaRepository<RentalFeedback,Integer> {

    public Optional<RentalFeedback> findRentalFeedbackByStudentIdAndApartmentId(Integer studentId, Integer apartmentId);

    public List<RentalFeedback> findAllByStudentId(Integer studentId);

    public List<RentalFeedback> findAllByApartmentId(Integer apartmentId);

    public List<RentalFeedback> findAllByStudentEmail(String email);
    @Query("SELECT rf FROM RentalFeedback rf WHERE rf.apartment.id=:apartmentId and rf.rating <= :value")
    public List<RentalFeedback> findAllByApartmentIdAndRatingLessThan(Integer apartmentId, Integer value);

    @Query("SELECT rf FROM RentalFeedback rf WHERE rf.apartment.id=:apartmentId and rf.rating >= :value")
    public List<RentalFeedback> findAllByApartmentIdAndRatingGreaterThan(Integer apartmentId, Integer value);
}
