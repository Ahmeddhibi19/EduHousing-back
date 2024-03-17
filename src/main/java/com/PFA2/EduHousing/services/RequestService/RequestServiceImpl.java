package com.PFA2.EduHousing.services.RequestService;

import com.PFA2.EduHousing.dto.Requestdto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.RentalDetails;
import com.PFA2.EduHousing.model.Request;
import com.PFA2.EduHousing.model.Status;
import com.PFA2.EduHousing.model.Student;
import com.PFA2.EduHousing.repository.RentalDetailsRepository;
import com.PFA2.EduHousing.repository.RequestRepository;
import com.PFA2.EduHousing.repository.StudentRepository;
import com.PFA2.EduHousing.validator.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService{
    private final RequestRepository requestRepository;
    private final StudentRepository studentRepository;
    private final RentalDetailsRepository rentalDetailsRepository;
    @Autowired
    public RequestServiceImpl(
            RequestRepository requestRepository,
            StudentRepository studentRepository,
            RentalDetailsRepository rentalDetailsRepository
    ){
        this.requestRepository=requestRepository;
        this.rentalDetailsRepository=rentalDetailsRepository;
        this.studentRepository=studentRepository;
    }



    @Override
    public Requestdto save(Requestdto requestdto, Integer studentId, Integer rentalDetailsId) {
        List<String> errors= RequestValidator.validate(requestdto);
        if(!errors.isEmpty()){
            log.error("invalid request {}",errors);
            throw new InvalidEntityException("invalid request", ErrorCodes.REQUEST_NOT_VALID,errors);
        }
        Student student=studentRepository.findById(studentId).orElseThrow(
                ()->new EntityNotFoundException("no student with this id :"+studentId,
                        ErrorCodes.STUDENT_NOT_FOUND)
        );
        RentalDetails rentalDetails=rentalDetailsRepository.findById(rentalDetailsId).orElseThrow(
                ()->new EntityNotFoundException("no rental details with this id :"+rentalDetailsId,
                        ErrorCodes.RENTAL_DETAILS_NOT_FOUND)
        );
        Optional<Request> existingRequest = requestRepository.getRequestByStudentIdAndRentalDetailsId(studentId, rentalDetailsId);
        if (existingRequest.isPresent()) {
            throw new InvalidEntityException("request already exists", ErrorCodes.REQUEST_ALL_READY_EXISTS);
        }

        Request request=Requestdto.toEntity(requestdto);
        request.setStatus(Status.PENDING);
        request.setStudent(student);
        student.getRequestSet().add(request);
        request.setRentalDetails(rentalDetails);
        rentalDetails.getRequestSet().add(request);
        studentRepository.save(student);
        rentalDetailsRepository.save(rentalDetails);
        return Requestdto.fromEntity(requestRepository.save(request));
    }

    @Override
    public Requestdto findById(Integer id) {
        if(id==null){
            log.error("request id is null");
            return null;
        }
        Optional<Request> request=requestRepository.findById(id);
        return Optional.of(Requestdto.fromEntity(request.get())).orElseThrow(
                ()->new EntityNotFoundException("no request with this id :"+id,
                        ErrorCodes.REQUEST_NOT_FOUND)
        );
    }

    @Override
    public List<Requestdto> findAllByStudentId(Integer studentId) {
        if (studentId==null){
            log.error("student id is null");
            return null;
        }
        return requestRepository.findAllByStudentId(studentId).stream()
                .map(Requestdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Requestdto getRequestByStudentIdAndRentalDetailsId(Integer studentId, Integer rentalDetailsId) {
        if (studentId==null){
            log.error("student id is null");
            return null;
        }
        if (rentalDetailsId==null){
            log.error("rental details id is null");
            return null;
        }
        Optional<Request> request=requestRepository.getRequestByStudentIdAndRentalDetailsId(studentId,rentalDetailsId);
        return Optional.of(Requestdto.fromEntity(request.get())).orElseThrow(
                ()->new EntityNotFoundException("no request for this student id :"+studentId+" or for this rental detals id :"+rentalDetailsId,
                        ErrorCodes.REQUEST_NOT_FOUND)
        );
    }
    @Override
    public List<Requestdto> findAllByRentalDetailsId(Integer rentalDetailsId) {
        if (rentalDetailsId==null){
            log.error("rental details id is null");
            return null;
        }
        return requestRepository.findAllByRentalDetailsId(rentalDetailsId).stream()
                .map(Requestdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Requestdto getValidatedRequestByRentalDetailsId(Integer rentalDetailsId) {
        if (rentalDetailsId==null){
            log.error("rental details id is null");
            return null;
        }
        Optional<Request> request= requestRepository.getValidatedRequestByRentalDetailsId(rentalDetailsId);
        return Optional.of(Requestdto.fromEntity(request.get())).orElseThrow(
                ()->new EntityNotFoundException("no validated request for this rental detals id :"+rentalDetailsId,
                        ErrorCodes.REQUEST_NOT_FOUND)
        );
    }

    @Override
    public List<Requestdto> getAllValidatedRequestsByStudentId(Integer studentID) {
        if (studentID==null){
            log.error("student id is null");
            return null;
        }
        return requestRepository.getAllValidatedRequestsByStudentId(studentID).stream()
                .map(Requestdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void acceptRequest(Integer requestId,Integer rentalDetailsId) {
        if (requestId==null){
            log.error("request Id is null");
            return;
        }
        if (rentalDetailsId==null){
            log.error("rental Details Id is null");
            return;
        }
        Request request=requestRepository.findById(requestId).orElseThrow(
                ()->new EntityNotFoundException("no request with this id :"+requestId,
                        ErrorCodes.REQUEST_NOT_FOUND)
        );
        RentalDetails rentalDetails=rentalDetailsRepository.findById(rentalDetailsId).orElseThrow(
                ()->new EntityNotFoundException("no rental details with this id :"+rentalDetailsId,
                        ErrorCodes.RENTAL_DETAILS_NOT_FOUND)
        );
        Optional<Request> existingAcceptedRequest=requestRepository.getRequestsByAcceptanceAndRentalDetailsId(rentalDetails.getId());
        if(existingAcceptedRequest.isPresent()){
            log.error("there is all ready an accepted request");
            throw new InvalidEntityException("there is all ready an accepted request",
                    ErrorCodes.ACCEPTED_REQUEST_ALL_READY_EXISTS);
        }
        Instant acceptanceTime = Instant.now();
        request.setStatus(Status.ACCEPTED);
        request.setAcceptanceTime(acceptanceTime);
        Instant acceptanceDelay = acceptanceTime.plus(Duration.ofDays(21));
        request.setAcceptanceDelay(acceptanceDelay);
        requestRepository.save(request);

    }

    @Override
    public void removeAcceptance(Integer requestId) {
        if(requestId==null){
            log.error("request id is null");
            return;
        }
        Request request=requestRepository.findById(requestId).orElseThrow(
                ()->new EntityNotFoundException("no request with this id :"+requestId,
                        ErrorCodes.REQUEST_NOT_FOUND)
        );
        if(request.getStatus()==Status.ACCEPTED){
            request.setStatus(Status.PENDING);
            request.setAcceptanceTime(null);
            request.setAcceptanceDelay(null);
            requestRepository.save(request);
        }else {
            log.error("the request is not accepted");
            return;
        }

    }

    @Override
    public void validateRequest(Integer requestId) {
        if(requestId==null){
            log.error("request id is null");
            return;
        }
        Request request=requestRepository.findById(requestId).orElseThrow(
                ()->new EntityNotFoundException("no request with this id :"+requestId,
                        ErrorCodes.REQUEST_NOT_FOUND)
        );
        if(request.getStatus()==Status.ACCEPTED){
            Instant validationTime=Instant.now();
            request.setStatus(Status.VALIDATED);
            request.setValidationTime(validationTime);
            request.setValidationDelay(request.getRentalDetails().getEndDate().toInstant());
            requestRepository.save(request);
        }else {
            log.error("cannot validate non accepted requests ");
            return;
        }

    }

    @Override
    public Requestdto update(Requestdto requestdto) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Scheduled(fixedRate = 60000)
    public void cleanupRequests(){
        Instant now = Instant.now();
        List<Request> requestsToRemove = requestRepository.findAll().stream()
                .filter(request ->request.getStatus()==Status.ACCEPTED && request.getAcceptanceDelay() != null && request.getAcceptanceDelay().isBefore(now))
                .collect(Collectors.toList());

        for (Request request : requestsToRemove) {
            if (request.getStatus() == Status.ACCEPTED) {
                //log.info("Skipping deletion of validated request with ID {}", request.getId());
                continue; // Skip deletion if request is validated
            }

            // Remove the request
            requestRepository.delete(request);
            log.info("Request with ID {} removed after acceptance delay", request.getId());
        }
    }
}
