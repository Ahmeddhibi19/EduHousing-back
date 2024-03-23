package com.PFA2.EduHousing.configuration;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.RentalDetails;
import com.PFA2.EduHousing.model.Request;
import com.PFA2.EduHousing.model.Status;
import com.PFA2.EduHousing.repository.ApartmentRepository;
import com.PFA2.EduHousing.repository.RentalDetailsRepository;
import com.PFA2.EduHousing.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Auto {
    private final RequestRepository requestRepository;
    private final RentalDetailsRepository rentalDetailsRepository;
    private final ApartmentRepository apartmentRepository;
    @Autowired
    public Auto(
            RequestRepository requestRepository,
            RentalDetailsRepository rentalDetailsRepository,
            ApartmentRepository apartmentRepository
    ){
        this.requestRepository=requestRepository;
        this.rentalDetailsRepository=rentalDetailsRepository;
        this.apartmentRepository= apartmentRepository;
    }
    @Scheduled(fixedRate = 60000)
    public void cleanupRequests(){
        Instant now = Instant.now();
        List<Request> requestsToRemove = requestRepository.findAll().stream()
                .filter(request ->request.getStatus()== Status.ACCEPTED && request.getAcceptanceDelay() != null && request.getAcceptanceDelay().isBefore(now))
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
    @Scheduled(fixedRate = 60000)
    public void cleanupRentalDeatails(){
        Date currentDate=  Calendar.getInstance().getTime();
        List<RentalDetails> rentalDetailsToRemove = rentalDetailsRepository.findAll().stream()
                .filter(rentalDetails ->
                        rentalDetails.getRequestSet().stream()
                                .noneMatch(request -> request.getStatus() == Status.VALIDATED || request.getStatus()==Status.ACCEPTED)
                                && rentalDetails.getStartDate().before(currentDate) && rentalDetails.getIsCurrent())
                .collect(Collectors.toList());
        for (RentalDetails rentalDetails:rentalDetailsToRemove){
            rentalDetailsRepository.delete(rentalDetails);
            log.info("rental Details with ID {} removed due to non acceptance or validation", rentalDetails.getId());
        }
    }
    @Scheduled(fixedRate = 60000)
    public void rentalStatusUpdate(){
        Date currentDate=  Calendar.getInstance().getTime();
        List<RentalDetails> rentalDetailsToUpdate = rentalDetailsRepository.findAll().stream()
                .filter(rentalDetails -> rentalDetails.getEndDate().before(currentDate) && rentalDetails.getRequestSet().stream()
                        .anyMatch(request -> request.getStatus()==Status.VALIDATED)


                )
                .collect(Collectors.toList());
        for (RentalDetails rentalDetails:rentalDetailsToUpdate){
            if (rentalDetails.getIsCurrent()){
                rentalDetails.setIsCurrent(false);
                Apartment apartment=rentalDetails.getApartment();
                apartment.setIsRented(false);
                apartmentRepository.save(apartment);
                rentalDetailsRepository.save(rentalDetails);
            }
        }
    }
}
