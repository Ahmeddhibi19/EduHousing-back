package com.PFA2.EduHousing.services.RentalFeedbackService;

import com.PFA2.EduHousing.dto.RentalFeedbackdto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.RentalFeedback;
import com.PFA2.EduHousing.model.Student;
import com.PFA2.EduHousing.repository.jpa.ApartmentRepository;
import com.PFA2.EduHousing.repository.jpa.RentalFeedbackRepository;
import com.PFA2.EduHousing.repository.jpa.StudentRepository;
import com.PFA2.EduHousing.validator.RentalFeedbackValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RentalFeedbackServiceImpl implements RentalFeedbackService{
    private final RentalFeedbackRepository rentalFeedbackRepository;
    private final StudentRepository studentRepository;
    private final ApartmentRepository apartmentRepository;
    public RentalFeedbackServiceImpl(
            RentalFeedbackRepository rentalFeedbackRepository,
            StudentRepository studentRepository,
            ApartmentRepository apartmentRepository
    ){
        this.rentalFeedbackRepository=rentalFeedbackRepository;
        this.apartmentRepository=apartmentRepository;
        this.studentRepository=studentRepository;
    }
    @Override
    public RentalFeedbackdto save(RentalFeedbackdto rentalFeedbackdto, Integer studentId,Integer apartmentId) {
        Student student=studentRepository.findById(studentId).orElseThrow(
                ()->new EntityNotFoundException("no student with this id :"+studentId,
                        ErrorCodes.STUDENT_NOT_FOUND)
        );
        Apartment apartment=apartmentRepository.findById(apartmentId).orElseThrow(
                ()->new EntityNotFoundException("no apartment with this Id :"+apartmentId,
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
        List<String> errors= RentalFeedbackValidator.validate(rentalFeedbackdto);
        if(!errors.isEmpty()){
            log.error("rental feedback is not valid {}",rentalFeedbackdto);
            throw new InvalidEntityException("rental feedback is not valid ",
                    ErrorCodes.RENTAL_FEEDBACK_NOT_VALID,
                    errors
            );
        }
        RentalFeedback rentalFeedback=RentalFeedbackdto.toEntity(rentalFeedbackdto);
        rentalFeedback.setApartment(apartment);
        apartment.getRentalFeedbackSet().add(rentalFeedback);
        rentalFeedback.setStudent(student);
        student.getRentalFeedbackSet().add(rentalFeedback);
        /*double currentRating = apartment.getRating() != null ? apartment.getRating() : 0.0;
        int feedbackCount = apartment.getRentalFeedbackSet().size();
        Double newRating = (currentRating * feedbackCount + rentalFeedback.getRating()) / (feedbackCount + 1);
        apartment.setRating(newRating);*/
        double newRating=0.0;
        Integer sum=0;
        for(RentalFeedback rental:apartment.getRentalFeedbackSet()){
            sum+=rental.getRating();
        }
        newRating= ((double) sum) /apartment.getRentalFeedbackSet().size();
        apartment.setRating(newRating);

        return RentalFeedbackdto.fromEntity(rentalFeedbackRepository.save(rentalFeedback));
    }

    @Override
    public RentalFeedbackdto findByStudentAndApartment(Integer studentId, Integer apartmentId) {
        if(studentId==null){
            log.error("student id is null");
            return null;
        }
        if(apartmentId==null){
            log.error("apartment id is null");
            return null;
        }
        Optional<RentalFeedback> rentalFeedback= rentalFeedbackRepository.findRentalFeedbackByStudentIdAndApartmentId(studentId, apartmentId);
        return Optional.of(RentalFeedbackdto.fromEntity(rentalFeedback.get())).orElseThrow(
                ()->new EntityNotFoundException("no rental feed back with this apartment id :"+apartmentId+" or with this " +
                        "student id :"+studentId,
                        ErrorCodes.RENTAL_FEEDBACK_NOT_FOUND)
        );
    }

    @Override
    public RentalFeedbackdto findById(Integer id) {
        if(id==null){
            log.error("rental feedback id is null");
            return null;
        }
        Optional<RentalFeedback> rentalFeedback=rentalFeedbackRepository.findById(id);
        return Optional.of(RentalFeedbackdto.fromEntity(rentalFeedback.get())).orElseThrow(
                ()->new EntityNotFoundException("no rental feedback with this id :"+id,
                        ErrorCodes.RENTAL_FEEDBACK_NOT_FOUND)
        );
    }

    @Override
    public List<RentalFeedbackdto> findAllByStudentId(Integer studentId) {
        if(studentId==null){
            log.error("student id is null");
            return null;
        }
        return rentalFeedbackRepository.findAllByStudentId(studentId).stream()
                .map(RentalFeedbackdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalFeedbackdto> findAllByApartmentId(Integer apartmentId) {
        if(apartmentId==null){
            log.error("apartment id is null");
            return null;
        }
        return rentalFeedbackRepository.findAllByApartmentId(apartmentId).stream()
                .map(RentalFeedbackdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalFeedbackdto> getRentalFeedbackByApartmentIdAndRatingLessThan(Integer apartmentId, Integer value) {
        if(apartmentId==null){
            log.error("apartment id is null");
            return null;
        }
        return rentalFeedbackRepository.findAllByApartmentIdAndRatingLessThan(apartmentId,value).stream()
                .map(RentalFeedbackdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalFeedbackdto> getRentalFeedbackByApartmentIdAndRatingGreaterThan(Integer apartmentId, Integer value) {
        if(apartmentId==null){
            log.error("apartment id is null");
            return null;
        }
        return rentalFeedbackRepository.findAllByApartmentIdAndRatingGreaterThan(apartmentId,value).stream()
                .map(RentalFeedbackdto::fromEntity)

                .collect(Collectors.toList());
    }
    @Override
    public void deleteById(Integer id){
        if(id==null){
            log.error("rental feedback id is null");
            return;
        }
        rentalFeedbackRepository.deleteById(id);
    }
    @Override
    public RentalFeedbackdto update(RentalFeedbackdto rentalFeedbackdto){
        RentalFeedback rentalFeedback= rentalFeedbackRepository.findById(rentalFeedbackdto.getId()).orElseThrow(
                ()->new EntityNotFoundException("no rental feedback found with this id :"+rentalFeedbackdto.getId(),
                        ErrorCodes.RENTAL_FEEDBACK_NOT_FOUND)
        );
        rentalFeedback.setContent(
                rentalFeedbackdto.getContent()!=null?
                        rentalFeedbackdto.getContent() : rentalFeedback.getContent()
        );
        rentalFeedback.setRating(
                rentalFeedbackdto.getRating()!=null?
                        rentalFeedbackdto.getRating(): rentalFeedback.getRating()
        );

        return RentalFeedbackdto.fromEntity(rentalFeedbackRepository.save(rentalFeedback));
    }


}
