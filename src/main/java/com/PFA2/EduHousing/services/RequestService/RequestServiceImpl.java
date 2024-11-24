package com.PFA2.EduHousing.services.RequestService;

import com.PFA2.EduHousing.dto.Requestdto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.*;
import com.PFA2.EduHousing.repository.jpa.ApartmentRepository;
import com.PFA2.EduHousing.repository.jpa.RentalDetailsRepository;
import com.PFA2.EduHousing.repository.jpa.RequestRepository;
import com.PFA2.EduHousing.repository.jpa.StudentRepository;
import com.PFA2.EduHousing.validator.RequestValidator;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService{
    private final RequestRepository requestRepository;
    private final StudentRepository studentRepository;
    private final RentalDetailsRepository rentalDetailsRepository;
    private final ApartmentRepository apartmentRepository;
    private final EntityManager entityManager;
    @Autowired
    public RequestServiceImpl(
            RequestRepository requestRepository,
            StudentRepository studentRepository,
            RentalDetailsRepository rentalDetailsRepository,
            ApartmentRepository apartmentRepository,
            EntityManager entityManager
    ){
        this.requestRepository=requestRepository;
        this.rentalDetailsRepository=rentalDetailsRepository;
        this.studentRepository=studentRepository;
        this.apartmentRepository=apartmentRepository;
        this.entityManager=entityManager;
    }



    @Override
    public Requestdto save(Requestdto requestdto, Integer studentId, Integer rentalDetailsId) {
        List<String> errors = RequestValidator.validate(requestdto);
        if (!errors.isEmpty()) {
            log.error("invalid request {}", errors);
            throw new InvalidEntityException("invalid request", ErrorCodes.REQUEST_NOT_VALID, errors);
        }

        // Fetch student and rentalDetails
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new EntityNotFoundException("no student with this id :" + studentId, ErrorCodes.STUDENT_NOT_FOUND)
        );
        RentalDetails rentalDetails = rentalDetailsRepository.findById(rentalDetailsId).orElseThrow(
                () -> new EntityNotFoundException("no rental details with this id :" + rentalDetailsId, ErrorCodes.RENTAL_DETAILS_NOT_FOUND)
        );

        // Check if request already exists
        Optional<Request> existingRequest = requestRepository.getRequestByStudentIdAndRentalDetailsId(studentId, rentalDetailsId);
        if (existingRequest.isPresent()) {
            throw new InvalidEntityException("request already exists", ErrorCodes.REQUEST_ALL_READY_EXISTS);
        }

        // Map DTO to entity
        Request request = Requestdto.toEntity(requestdto);
        request.setStatus(Status.PENDING);
        request.setStudent(student);
        request.setRentalDetails(rentalDetails);

        // Add request to student and rentalDetails
        student.getRequestSet().add(request);
        rentalDetails.getRequestSet().add(request);

        // Save request and cascade changes to student and rentalDetails
        request = requestRepository.save(request);

        // Return DTO representation of saved request
        return Requestdto.fromEntity(request);
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
        Request request= requestRepository.getValidatedRequestByRentalDetailsId(rentalDetailsId).orElseThrow(
                ()->new EntityNotFoundException("no validated request for this rental details id :"+rentalDetailsId,
                        ErrorCodes.RENTAL_DETAILS_NOT_FOUND)
        );
        return Requestdto.fromEntity(request);


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
        Optional<Request> existingValidatedRequest=requestRepository.getRequestsByValidationAndRentalDetailsId(rentalDetails.getId());
        if(existingValidatedRequest.isPresent()){
            log.error("there is all ready validated request");
            throw new InvalidEntityException("there is all ready validated request",
                    ErrorCodes.RENTAL_DETAILS_HAS_ALREADY_VALIDATED_REQUEST);
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
            Apartment apartment= request.getRentalDetails().getApartment();
            apartment.setIsRented(true);
            apartmentRepository.save(apartment);
            requestRepository.save(request);
        }else {
            log.error("cannot validate non accepted requests ");
            throw new InvalidEntityException("cannot validate non accepted requests",
                    ErrorCodes.CANNOT_VALIDATE_NON_ACCEPTED_REQUESTS);


        }

    }

    @Override
    public Requestdto update(Requestdto requestdto) {
        if (requestdto.getId()==null){
            log.error("new request id is null");
            return null;
        }
        Request request=requestRepository.findById(requestdto.getId()).orElseThrow(
                ()->new EntityNotFoundException("no request with this id :"+requestdto.getId(),
                        ErrorCodes.REQUEST_NOT_FOUND)
        );
        request.setContent(
                requestdto.getContent()!=null?
                        requestdto.getContent(): request.getContent()
                );
        return Requestdto.fromEntity(requestRepository.save(request));
    }

    @Override
    public void deleteById(Integer id) {
        if (id==null){
            log.error("request id is null");
            return;
        }
        Request request=requestRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("no request with this id :"+id,
                        ErrorCodes.REQUEST_NOT_FOUND)
        );
        if(request.getStatus()==Status.ACCEPTED || request.getStatus()==Status.VALIDATED){
            log.error("cannot delete accepted or validated requests {}",request);
            throw new InvalidEntityException("cannot delete accepted or validated requests",
                    ErrorCodes.COULD_NOT_DELETE_VALIDATED_OR_ACCEPTED_REQUEST);
        }
        requestRepository.delete(request);

    }

    @Override
    public void rejectRequest(Integer requestId,Integer rentalDetailsId) {
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
        if(request.getStatus()!=Status.ACCEPTED && request.getStatus()!=Status.VALIDATED){
            request.setStatus(Status.REJECTED);
            requestRepository.save(request);
        }else {
            log.error("cannot reject accepted or validated requests {}",request);
            throw new InvalidEntityException("cannot reject accepted or validated requests",
                    ErrorCodes.CANNOT_REJECT_VALIDATED_OR_ACCEPTED_REQUESTS);
        }

    }

   /* @Scheduled(fixedRate = 60000)
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
    }*/
}
