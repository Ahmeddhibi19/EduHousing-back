package com.PFA2.EduHousing.services.RequestService;

import com.PFA2.EduHousing.dto.Requestdto;
import com.PFA2.EduHousing.model.Request;

import java.util.List;

public interface RequestService {
    public Requestdto save(Requestdto requestdto,Integer studentId,Integer rentalDetailsId);
    public Requestdto findById(Integer id);
    public List<Requestdto> findAllByStudentId(Integer studentId);
    public Requestdto getRequestByStudentIdAndRentalDetailsId(Integer studentId,Integer rentalDetailsId);
    public List<Requestdto> findAllByRentalDetailsId(Integer rentalDetailsId);
    public Requestdto getValidatedRequestByRentalDetailsId(Integer rentalDetailsId);
    public List<Requestdto> getAllValidatedRequestsByStudentId(Integer studentID);
    public void acceptRequest(Integer requestId,Integer rentalDetailsId);
    public void removeAcceptance(Integer requestId);
    public void validateRequest(Integer requestId);
    public Requestdto update(Requestdto requestdto);
    public void deleteById(Integer id);


}
