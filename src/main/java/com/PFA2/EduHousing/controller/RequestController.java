package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.RequestApi;
import com.PFA2.EduHousing.dto.Requestdto;
import com.PFA2.EduHousing.services.RequestService.RequestService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController implements RequestApi {
    private final RequestService requestService;
    public RequestController(RequestService requestService){
        this.requestService=requestService;
    }
    @Override
    public Requestdto save(Requestdto requestdto, Integer studentId, Integer rentalDetailsId) {
        return requestService.save(requestdto,studentId,rentalDetailsId);
    }

    @Override
    public Requestdto findById(Integer id) {
        return requestService.findById(id);
    }

    @Override
    public List<Requestdto> findAllByStudentId(Integer studentId) {
        return requestService.findAllByStudentId(studentId);
    }

    @Override
    public Requestdto getRequestByStudentIdAndRentalDetailsId(Integer studentId, Integer rentalDetailsId) {
        return requestService.getRequestByStudentIdAndRentalDetailsId(studentId, rentalDetailsId);
    }

    @Override
    public List<Requestdto> findAllByRentalDetailsId(Integer rentalDetailsId) {
        return requestService.findAllByRentalDetailsId(rentalDetailsId);
    }

    @Override
    public Requestdto getValidatedRequestByRentalDetailsId(Integer rentalDetailsId) {
        return requestService.getValidatedRequestByRentalDetailsId(rentalDetailsId);
    }

    @Override
    public List<Requestdto> getAllValidatedRequestsByStudentId(Integer studentID) {
        return requestService.getAllValidatedRequestsByStudentId(studentID);
    }

    @Override
    public void acceptRequest(Integer requestId, Integer rentalDetailsId) {
        requestService.acceptRequest(requestId,rentalDetailsId);
    }

    @Override
    public void removeAcceptance(Integer requestId) {
        requestService.removeAcceptance(requestId);
    }

    @Override
    public void validateRequest(Integer requestId) {
        requestService.validateRequest(requestId);
    }

    @Override
    public Requestdto update(Requestdto requestdto) {
        return requestService.update(requestdto);
    }

    @Override
    public void deleteById(Integer id) {
        requestService.deleteById(id);
    }

    @Override
    public void rejectRequest(Integer requestId, Integer rentalDetailsId) {
        requestService.rejectRequest(requestId,rentalDetailsId);
    }
}
