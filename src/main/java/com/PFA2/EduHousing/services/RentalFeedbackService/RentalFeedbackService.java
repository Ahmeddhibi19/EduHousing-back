package com.PFA2.EduHousing.services.RentalFeedbackService;

import com.PFA2.EduHousing.dto.RentalFeedbackdto;
import com.PFA2.EduHousing.model.RentalFeedback;

import java.util.List;

public interface RentalFeedbackService {
    public RentalFeedbackdto save(RentalFeedbackdto rentalFeedbackdto,Integer studentId,Integer apartmentId);
    public RentalFeedbackdto findByStudentAndApartment(Integer studentId,Integer apartmentId);
    public RentalFeedbackdto findById(Integer id);
    public List<RentalFeedbackdto> findAllByStudentId(Integer studentId);
    public List<RentalFeedbackdto> findAllByApartmentId(Integer apartmentId);
    public List<RentalFeedbackdto> getRentalFeedbackByApartmentIdAndRatingLessThan(Integer apartmentId,Integer value);
    public List<RentalFeedbackdto> getRentalFeedbackByApartmentIdAndRatingGreaterThan(Integer apartmentId,Integer value);
}
