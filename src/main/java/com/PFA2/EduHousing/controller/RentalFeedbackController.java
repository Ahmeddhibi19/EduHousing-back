package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.RentalFeedbackApi;
import com.PFA2.EduHousing.dto.RentalFeedbackdto;
import com.PFA2.EduHousing.services.RentalFeedbackService.RentalFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RentalFeedbackController implements RentalFeedbackApi {
    private final RentalFeedbackService rentalFeedbackService;
    @Autowired
    public RentalFeedbackController(RentalFeedbackService rentalFeedbackService){
        this.rentalFeedbackService=rentalFeedbackService;
    }
    @Override
    public RentalFeedbackdto save(RentalFeedbackdto rentalFeedbackdto, Integer studentId, Integer apartmentId) {
        return rentalFeedbackService.save(rentalFeedbackdto,studentId,apartmentId);
    }

    @Override
    public RentalFeedbackdto findByStudentAndApartment(Integer studentId, Integer apartmentId) {
        return rentalFeedbackService.findByStudentAndApartment(studentId,apartmentId);
    }

    @Override
    public RentalFeedbackdto findById(Integer id) {
        return rentalFeedbackService.findById(id);
    }

    @Override
    public List<RentalFeedbackdto> findAllByStudentId(Integer studentId) {
        return rentalFeedbackService.findAllByStudentId(studentId);
    }

    @Override
    public List<RentalFeedbackdto> findAllByApartmentId(Integer apartmentId) {
        return rentalFeedbackService.findAllByApartmentId(apartmentId);
    }

    @Override
    public List<RentalFeedbackdto> getRentalFeedbackByApartmentIdAndRatingLessThan(Integer apartmentId, Integer value) {
        return rentalFeedbackService.getRentalFeedbackByApartmentIdAndRatingLessThan(apartmentId,value);
    }

    @Override
    public List<RentalFeedbackdto> getRentalFeedbackByApartmentIdAndRatingGreaterThan(Integer apartmentId, Integer value) {
        return rentalFeedbackService.getRentalFeedbackByApartmentIdAndRatingGreaterThan(apartmentId,value);
    }

    @Override
    public void deleteById(Integer id) {
        rentalFeedbackService.deleteById(id);
    }

    @Override
    public RentalFeedbackdto update(RentalFeedbackdto rentalFeedbackdto) {
        return rentalFeedbackService.update(rentalFeedbackdto);
    }
}
