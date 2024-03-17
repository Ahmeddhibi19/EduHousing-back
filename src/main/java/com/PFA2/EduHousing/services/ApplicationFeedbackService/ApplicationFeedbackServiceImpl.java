package com.PFA2.EduHousing.services.ApplicationFeedbackService;

import com.PFA2.EduHousing.dto.ApplicationFeedbackdto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.*;
import com.PFA2.EduHousing.repository.ApplicationFeedbackRepository;
import com.PFA2.EduHousing.repository.HomeownerRepository;
import com.PFA2.EduHousing.repository.StudentRepository;
import com.PFA2.EduHousing.repository.UserRepository;
import com.PFA2.EduHousing.validator.ApplicationFeedbackValidator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.InstanceFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationFeedbackServiceImpl implements ApplicationFeedbackService {
    private final ApplicationFeedbackRepository applicationFeedbackRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final HomeownerRepository homeownerRepository;

    @Autowired
    public ApplicationFeedbackServiceImpl(
            ApplicationFeedbackRepository applicationFeedbackRepository,
            UserRepository userRepository,
            StudentRepository studentRepository,
            HomeownerRepository homeownerRepository
    ){
        this.applicationFeedbackRepository=applicationFeedbackRepository;
        this.userRepository=userRepository;
        this.studentRepository=studentRepository;
        this.homeownerRepository=homeownerRepository;
    }
    @Override
    public ApplicationFeedbackdto save(ApplicationFeedbackdto applicationFeedbackdto, Integer userId) {
        if(userId==null){
            log.error("null user id");
            throw new IllegalArgumentException("user id is null");
        }
        User user=userRepository.findById(userId).orElseThrow(
                ()->new EntityNotFoundException("No user found with this Id :"+userId,
                        ErrorCodes.USER_NOT_FOUND
                )
        );

        if (!(user instanceof Student || user instanceof Homeowner)) {
            log.error("user is not student neither homeowner");
            throw new InvalidEntityException("user with this id :" + userId + " is not student neither homeowner",
                    ErrorCodes.USER_NOT_HOMEOWNER_NEITHER_STUDENT);
        }

        if (user instanceof Student && ((Student) user).getApplicationFeedback() != null) {
            log.error("Student already has an application feedback");
            throw new InvalidEntityException("Student already has an application feedback",
                    ErrorCodes.USER_ALREADY_HAS_APPLICATION_FEEDBACK);
        } else if (user instanceof Homeowner && ((Homeowner) user).getApplicationFeedback() != null) {
            log.error("Homeowner already has an application feedback");
            throw new InvalidEntityException("Homeowner already has an application feedback",
                    ErrorCodes.USER_ALREADY_HAS_APPLICATION_FEEDBACK);
        }

        List<String> errors = ApplicationFeedbackValidator.validate(applicationFeedbackdto);
        if(!errors.isEmpty()){
            log.error("invalid application's feedback {}",applicationFeedbackdto);
            throw new InvalidEntityException("invalid application's feedback",ErrorCodes.APPLICATION_FEEDBACK_NOT_VALID,errors);
        }

        ApplicationFeedback applicationFeedback=ApplicationFeedbackdto.toEntity(applicationFeedbackdto);
        if(user instanceof Student){
            applicationFeedback.setStudent((Student) user);
            ((Student) user).setApplicationFeedback(applicationFeedback);
            userRepository.save((Student) user);

        }else if(user  instanceof  Homeowner){
            applicationFeedback.setHomeowner((Homeowner) user);
            ((Homeowner) user).setApplicationFeedback(applicationFeedback);
            userRepository.save((Homeowner) user);
        }
        else {
            log.error("user is not student neither homeowner");
            throw new InvalidEntityException("user with this id :"+userId+" is not student neither homeowner",
                    ErrorCodes.USER_NOT_HOMEOWNER_NEITHER_STUDENT);
        }
        return ApplicationFeedbackdto.fromEntity(applicationFeedbackRepository.save(applicationFeedback));
    }

    @Override
    public ApplicationFeedbackdto findAppFeedbackByUser(Integer userId) {
        if(userId==null){
            log.error("user id is null");
            throw new IllegalArgumentException("user id is null");
        }


        Optional<ApplicationFeedback> applicationFeedback1=applicationFeedbackRepository.findApplicationFeedbackByStudentId(userId);
        Optional<ApplicationFeedback> applicationFeedback2=applicationFeedbackRepository.findApplicationFeedbackByHomeownerId(userId);

        Optional<ApplicationFeedback> applicationFeedback=applicationFeedback1.isPresent()?
                                                                applicationFeedback1 : applicationFeedback2 ;

        return Optional.of(ApplicationFeedbackdto.fromEntity(applicationFeedback.get())).orElseThrow(
                ()->new EntityNotFoundException("no application's feedback for this user id :"+userId,
                        ErrorCodes.APPLICATION_FEEDBACK_NOT_FOUND)
        );
    }

    @Override
    public List<ApplicationFeedbackdto> findAll() {
        return applicationFeedbackRepository.findAll().stream()
                .map(ApplicationFeedbackdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationFeedbackdto> findAllByUserName(String userName) {
        return applicationFeedbackRepository.findAllByFirstName(userName).stream()
                .map(ApplicationFeedbackdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationFeedbackdto update(ApplicationFeedbackdto applicationFeedbackdto) {

        if(applicationFeedbackdto.getId()==null){
            throw new IllegalArgumentException("City ID cannot be null");
        }
        ApplicationFeedback existingFeedback= applicationFeedbackRepository.findById(applicationFeedbackdto.getId())
                .orElseThrow(
                        ()->new EntityNotFoundException(
                                "City with this id :"+applicationFeedbackdto.getId()+" is not found",
                                ErrorCodes.APPLICATION_FEEDBACK_NOT_FOUND
                        )
                );
        existingFeedback.setRating(
                applicationFeedbackdto.getRating()!=null?
                        applicationFeedbackdto.getRating() : existingFeedback.getRating()
                );
        existingFeedback.setContent(
                applicationFeedbackdto.getContent()!=null?
                        applicationFeedbackdto.getContent() : existingFeedback.getContent()
                );

        return ApplicationFeedbackdto.fromEntity(applicationFeedbackRepository.save(existingFeedback));
    }

    @Override
    public void deleteById(Integer id) {
        if(id==null){
            log.error("application feedback id is null !!");
            return;
        }
        applicationFeedbackRepository.deleteById(id);
    }
}
