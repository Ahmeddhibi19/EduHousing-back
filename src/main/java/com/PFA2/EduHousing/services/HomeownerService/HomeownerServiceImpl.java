package com.PFA2.EduHousing.services.HomeownerService;


import com.PFA2.EduHousing.dto.Homeownerdto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.ConnexionStatus;
import com.PFA2.EduHousing.model.Homeowner;
import com.PFA2.EduHousing.model.Roles;
import com.PFA2.EduHousing.model.chat.MongoUser;
import com.PFA2.EduHousing.repository.jpa.HomeownerRepository;
import com.PFA2.EduHousing.repository.mongo.MonGoUserRepository;
import com.PFA2.EduHousing.validator.HomeownerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HomeownerServiceImpl implements HomeownerService{
    private final HomeownerRepository homeownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MonGoUserRepository monGoUserRepository;

   @Autowired
   public HomeownerServiceImpl(HomeownerRepository homeownerRepository,PasswordEncoder passwordEncoder,MonGoUserRepository monGoUserRepository){
       this.homeownerRepository=homeownerRepository;
       this.passwordEncoder=passwordEncoder;
       this.monGoUserRepository=monGoUserRepository;
   }
    @Override
    public Homeownerdto save(Homeownerdto homeownerdto) {

       List<String> errors= HomeownerValidator.validate(homeownerdto);

        if(!errors.isEmpty()){
            log.error("Student is not valid {}",homeownerdto);
            throw new InvalidEntityException("Homeowner is not valid", ErrorCodes.HOMEOWNER_NOT_VALID,errors);
        }
        Optional<Homeowner> homeownerEmail= homeownerRepository.findHomeownerByEmail(homeownerdto.getEmail());
        if(homeownerEmail.isPresent()){
            log.error("homeowner's email all ready exists {}",homeownerdto);
            throw new InvalidEntityException("User with this email all ready exists ",ErrorCodes.USER_ALL_READY_EXISTS);
        }
        Optional<Homeowner> homeownerPhoneNumber= homeownerRepository.findHomeownerByPhoneNumber(homeownerdto.getPhoneNumber());
        if(homeownerPhoneNumber.isPresent()){
            log.error("homeowner's phone number all ready exists {}",homeownerdto);
            throw new InvalidEntityException("User with this email all ready exists with this email",ErrorCodes.USER_ALL_READY_EXISTS);
        }
        homeownerdto.setRole(Roles.HOMEOWNER);
        homeownerdto.setPassword(passwordEncoder.encode(homeownerdto.getPassword()));
        homeownerdto.setStatus(ConnexionStatus.ONLINE);
        MongoUser mongoUser=MongoUser.builder()
               // .id(homeownerdto.getId().toString())
                .fullName(homeownerdto.getFirstName()+" "+homeownerdto.getLastName())
                .status(homeownerdto.getStatus())
                .build();
        monGoUserRepository.save(mongoUser);
        //monGoUserRepository.save(Homeownerdto.toEntity(homeownerdto));
        return Homeownerdto.fromEntity(
                homeownerRepository.save(
                        Homeownerdto.toEntity(homeownerdto)
                )
        );
    }

    @Override
    public Homeownerdto findById(Integer homeownerId) {

       if(homeownerId==null){
           log.error("Homeowner id is null !!");
           return null;
       }
       Optional<Homeowner> homeowner=homeownerRepository.findById(homeownerId);
        return Optional.of(Homeownerdto.fromEntity(homeowner.get())).orElseThrow(
                    ()-> new EntityNotFoundException("no such homeowner with this id : "+homeownerId+" !!!",
                    ErrorCodes.HOMEOWNER_NOT_FOUND
                )
        ) ;
    }

    @Override
    public Homeownerdto findByEmail(String email) {

       if(!StringUtils.hasLength(email)){
           log.error("Homeowner email is null !!");
           return null;
       }
       Optional<Homeowner> homeowner=homeownerRepository.findHomeownerByEmail(email);
        return Optional.of(Homeownerdto.fromEntity(homeowner.get())).orElseThrow(
                ()-> new EntityNotFoundException("no such homeowner with this email : "+email+" !!!",
                        ErrorCodes.HOMEOWNER_NOT_FOUND
                )
        ) ;
    }

    @Override
    public List<Homeownerdto> findByFirstName(String firstName) {

       if(!StringUtils.hasLength(firstName)){
           log.error("No homeowners first name provided");
           return null;
       }

        return homeownerRepository.findAllByFirstName(firstName).stream()
                .map(Homeownerdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Homeownerdto> findAll() {
        return homeownerRepository.findAll().stream()
                .map(Homeownerdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Homeownerdto findByPhoneNumber(String phoneNumber) {
        if(!StringUtils.hasLength(phoneNumber)){
            log.error("Homeowner phone number is null !!");
            return null;
        }
        Optional<Homeowner> homeowner=homeownerRepository.findHomeownerByPhoneNumber(phoneNumber);
        return Optional.of(Homeownerdto.fromEntity(homeowner.get())).orElseThrow(
                ()-> new EntityNotFoundException("no such homeowner with this phone number : "+phoneNumber+" !!!",
                        ErrorCodes.HOMEOWNER_NOT_FOUND
                )
        ) ;
    }

    @Override
    public Homeownerdto update(Homeownerdto homeownerdto) {

       if (homeownerdto.getId()==null){
           throw new IllegalArgumentException("Homeowner ID cannot be null");
       }

       Homeowner existingHomeowner= homeownerRepository.findById(homeownerdto.getId())
               .orElseThrow(
                       ()-> new EntityNotFoundException("no such homeowner with this id : "+homeownerdto.getId()+" !!!",
                       ErrorCodes.HOMEOWNER_NOT_FOUND)
               );

       existingHomeowner.setFirstName(StringUtils.hasLength(homeownerdto.getFirstName())?
               homeownerdto.getFirstName():existingHomeowner.getFirstName());

       existingHomeowner.setAddress(StringUtils.hasLength(homeownerdto.getAddress())?
               homeownerdto.getAddress(): existingHomeowner.getAddress());

       existingHomeowner.setLastName(StringUtils.hasLength(homeownerdto.getLastName())?
               homeownerdto.getLastName() : existingHomeowner.getLastName());

       existingHomeowner.setPhoneNumber(StringUtils.hasLength(homeownerdto.getPhoneNumber())?
               homeownerdto.getPhoneNumber() : existingHomeowner.getPhoneNumber());


       Homeowner updatedHomeowner= homeownerRepository.save(existingHomeowner);

        return Homeownerdto.fromEntity(updatedHomeowner);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        if(!StringUtils.hasLength(phoneNumber)){
            log.error("Homeowner phone number is null !!");
            return;
        }
        homeownerRepository.deleteHomeownerByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("Homeowner email is null !!");
            return;
        }
        homeownerRepository.deleteHomeownerByEmail(email);
    }

    @Override
    public void deleteById(Integer id){
        if(id==null){
            log.error("Homeowner id is null !!");
            return;
        }
        homeownerRepository.deleteById(id);
    }


}
