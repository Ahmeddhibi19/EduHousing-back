package com.PFA2.EduHousing.services.RentalDetailsService;

import com.PFA2.EduHousing.dto.RentalDetailsdto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.RentalDetails;
import com.PFA2.EduHousing.model.Request;
import com.PFA2.EduHousing.model.Status;
import com.PFA2.EduHousing.repository.jpa.ApartmentRepository;
import com.PFA2.EduHousing.repository.jpa.RentalDetailsRepository;
import com.PFA2.EduHousing.validator.RentalDetailsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RentalDetailsServiceImpl implements RentalDetailsService{
    private final ApartmentRepository apartmentRepository;
    private final RentalDetailsRepository rentalDetailsRepository;
    @Autowired
    public RentalDetailsServiceImpl(
            ApartmentRepository apartmentRepository,
            RentalDetailsRepository rentalDetailsRepository
    ){
        this.apartmentRepository=apartmentRepository;
        this.rentalDetailsRepository=rentalDetailsRepository;
    }

    @Override
    public RentalDetailsdto save(RentalDetailsdto rentalDetailsdto, Integer apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId).orElseThrow(
                ()->new EntityNotFoundException("no apartment with this id :"+apartmentId,
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
        List<String> errors = RentalDetailsValidator.validate(rentalDetailsdto);
        if(!errors.isEmpty()){
            log.error("rental details is not valid {}",rentalDetailsdto);
            throw new InvalidEntityException("rental details is not valid ", ErrorCodes.RENTAL_DETAILS_NOT_VALID, errors);

        }

        RentalDetails newRentalDetails = RentalDetailsdto.toEntity(rentalDetailsdto);
        List<RentalDetails> rentalDetailsList = apartment.getRentalDetailsList();

        if (!rentalDetailsList.isEmpty()) {
            // Sort the list by start date in descending order to get the latest saved rental details
            rentalDetailsList.sort(Comparator.comparing(RentalDetails::getStartDate).reversed());

            // Check if the new rental period starts after the end date of the latest saved rental details
            if (newRentalDetails.getStartDate().before(rentalDetailsList.get(0).getEndDate())) {
                throw new InvalidEntityException("Start date must be after the end date of the latest saved rental period",
                        ErrorCodes.RENTAL_DETAILS_NOT_VALID);
            }


            // Check for overlapping rental periods
            for (RentalDetails rentalDetails : rentalDetailsList) {
                if (
                        (newRentalDetails.getEndDate().before(rentalDetails.getStartDate()) &&
                        newRentalDetails.getStartDate().before(rentalDetails.getStartDate())) ||
                                (
                                        newRentalDetails.getEndDate().after(rentalDetails.getStartDate()) &&
                                                newRentalDetails.getStartDate().before(rentalDetails.getStartDate())
                                ) ||
                                (
                                        newRentalDetails.getEndDate().before(rentalDetails.getEndDate()) &&
                                                newRentalDetails.getStartDate().after(rentalDetails.getStartDate())
                                ) ||
                                (
                                        newRentalDetails.getEndDate().after(rentalDetails.getEndDate()) &&
                                                newRentalDetails.getStartDate().before(rentalDetails.getEndDate())
                                )
                ) {
                    throw new InvalidEntityException("Rental period overlaps with an existing rental period",
                            ErrorCodes.RENTAL_DETAILS_NOT_VALID);
                }
                if (rentalDetails.getIsCurrent()) {
                    throw new InvalidEntityException("There is already a current rental period for this apartment",
                            ErrorCodes.CURRENT_RENTAL_ALL_READY_EXISTS);
                }

            }
        }

        newRentalDetails.setIsCurrent(true);
        newRentalDetails.setApartment(apartment);
        apartment.getRentalDetailsList().add(newRentalDetails);
        apartmentRepository.save(apartment);
        return RentalDetailsdto.fromEntity(rentalDetailsRepository.save(newRentalDetails));
    }

    @Override
    public RentalDetailsdto findById(Integer id) {
        if(id==null){
            log.error(("rental details is null"));
            return null;
        }
        Optional<RentalDetails> rentalDetails=rentalDetailsRepository.findById(id);
        return Optional.of(RentalDetailsdto.fromEntity(rentalDetails.get())).orElseThrow(
                ()->new EntityNotFoundException("no rental details with this id :"+id,
                        ErrorCodes.RENTAL_DETAILS_NOT_FOUND)
        );
    }

    @Override
    public List<RentalDetailsdto> findAllByApartmentId(Integer apartmentId) {
        if(apartmentId==null){
            log.error("apartment id is null");
            return null;
        }

        return rentalDetailsRepository.findAllByApartmentId(apartmentId).stream()
                .map(RentalDetailsdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalDetailsdto> findRentalByPriceAndIsCurrent(Double val) {
        if(val==null){
            log.error("search price is null");
            return null;
        }
        if(val<=0){
            log.error("search price is negative");
            return null;
        }
        return rentalDetailsRepository.findRentalByPriceAndIsCurrent(val).stream()
                .map(RentalDetailsdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalDetailsdto> findRentalByStartDate(Date val) {
        if(val==null){
            log.error("start date is null");
            return null;
        }
        return rentalDetailsRepository.findRentalByStartDate(val).stream()
                .map(RentalDetailsdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalDetailsdto> getRentalHistoryByApartment(Integer apartmentId) {
        if(apartmentId==null){
            log.error("apartment id is null");
            return null;
        }
        return rentalDetailsRepository.getRentalHistoryByApartment(apartmentId).stream()
                .map(RentalDetailsdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalDetailsdto> getRentalDetailsByPeriod(Date startDate, Date endDate) {
        if(startDate==null){
            log.error("start date is null");
            return null;
        }
        return rentalDetailsRepository.getRentalDetailsByPeriod(startDate,endDate).stream()
                .map(RentalDetailsdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalDetailsdto> getRentalDetailsByStudentCollegeCity(Integer studentCollegeCityId) {
        if(studentCollegeCityId==null){
            log.error("the city id of the student college is null");
            return null;
        }
        return rentalDetailsRepository.getRentalDetailsByStudentCollegeCity(studentCollegeCityId).stream()
                .map(RentalDetailsdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalDetailsdto> getRentalHistoryByStudent(Integer studentId) {
        if(studentId==null){
            log.error("student id is null");
            return null;
        }
        return rentalDetailsRepository.getRentalHistoryByStudent(studentId).stream()
                .map(RentalDetailsdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RentalDetailsdto update(RentalDetailsdto rentalDetailsdto) {
        if(rentalDetailsdto.getId()==null){
            log.error("rental details id is null");
            return null;
        }
        RentalDetails rentalDetails=rentalDetailsRepository.findById(rentalDetailsdto.getId()).orElseThrow(
                ()->new EntityNotFoundException("no rental details with this id :"+rentalDetailsdto.getId(),
                        ErrorCodes.RENTAL_DETAILS_NOT_FOUND)
        );
        Set<Request> requestList = rentalDetails.getRequestSet();
        for (Request request:requestList){
            if (request.getStatus()== Status.VALIDATED){
                throw new InvalidEntityException("cannot update rental post with validated request",ErrorCodes.CANNOT_UPDATE_RENTAL_POST_WITH_VALIDATED_REQUESTS);
            }
        }
        rentalDetails.setMonthlyAmount(rentalDetailsdto.getMonthlyAmount() != null ?
                rentalDetailsdto.getMonthlyAmount() : rentalDetails.getMonthlyAmount());
        rentalDetails.setDescription(rentalDetailsdto.getDescription()!=null?
                rentalDetailsdto.getDescription():rentalDetails.getDescription());
        if (rentalDetailsdto.getStartDate()!=null){
            LocalDate today = LocalDate.now();
            LocalDate newStartDate = rentalDetailsdto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (newStartDate.isBefore(today)) {
                throw new InvalidEntityException("new Start date must be after today");
            }
            if(rentalDetailsdto.getEndDate()==null){
                if (rentalDetailsdto.getStartDate().before(rentalDetails.getEndDate())){
                    rentalDetails.setStartDate(rentalDetailsdto.getStartDate());
                }else {
                    throw new InvalidEntityException("new start date must be before old end date");
                }
            }else {
                if (rentalDetailsdto.getStartDate().before(rentalDetailsdto.getEndDate())){
                    rentalDetails.setStartDate(rentalDetailsdto.getStartDate());
                    rentalDetails.setEndDate(rentalDetailsdto.getEndDate());
                }else {
                    throw new InvalidEntityException("new start date must be before new end date");
                }
            }
        }
        if (rentalDetailsdto.getEndDate()!=null){
            if (rentalDetailsdto.getStartDate()==null){
                if (rentalDetailsdto.getEndDate().after(rentalDetails.getStartDate())){
                    rentalDetails.setEndDate(rentalDetailsdto.getEndDate());
                }else {
                    throw new InvalidEntityException("new end date must be after old start date");
                }
            }
            else {
                if (rentalDetailsdto.getStartDate().before(rentalDetailsdto.getEndDate())){
                    rentalDetails.setStartDate(rentalDetailsdto.getStartDate());
                    rentalDetails.setEndDate(rentalDetailsdto.getEndDate());
                }else {
                    throw new InvalidEntityException("new start date must be before new end date");
                }
            }
        }

        return RentalDetailsdto.fromEntity(rentalDetailsRepository.save(rentalDetails));
    }

    @Override
    public void deleteById(Integer id) {
        if (id==null){
            log.error("rental details id is null");
            return;
        }
        RentalDetails rentalDetails=rentalDetailsRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("no rental details with this id :"+id,
                        ErrorCodes.RENTAL_DETAILS_NOT_FOUND)
        );
        Set<Request> requestList = rentalDetails.getRequestSet();
        for (Request request:requestList){
            if (request.getStatus()== Status.VALIDATED){
                throw new InvalidEntityException("cannot delete rental post with validated request",ErrorCodes.CANNOT_DELETE_RENTAL_POST_WITH_VALIDATED_REQUESTS);
            }
        }
        rentalDetailsRepository.deleteById(id);
    }
}
