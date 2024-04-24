package com.PFA2.EduHousing.services.AdminService;

import com.PFA2.EduHousing.dto.Admindto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.Admin;
import com.PFA2.EduHousing.model.ConnexionStatus;
import com.PFA2.EduHousing.model.Roles;
import com.PFA2.EduHousing.model.chat.MongoUser;
import com.PFA2.EduHousing.repository.jpa.AdminRepository;
import com.PFA2.EduHousing.repository.mongo.MonGoUserRepository;
import com.PFA2.EduHousing.validator.AdminValidator;
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
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final MonGoUserRepository monGoUserRepository;

    @Autowired
    public AdminServiceImpl(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder,
            MonGoUserRepository monGoUserRepository
    ){
        this.adminRepository=adminRepository;
        this.passwordEncoder=passwordEncoder;
        this.monGoUserRepository=monGoUserRepository;
    }

    @Override
    public Admindto save(Admindto admindto) {
        List<String> errors= AdminValidator.validate(admindto);

        if(!errors.isEmpty()){
            log.error("Admin is not valid {}",admindto);
            throw new InvalidEntityException("Admin is not valid", ErrorCodes.ADMIN_NOT_VALID,errors);
        }
        Optional<Admin> adminEmail= adminRepository.findAdminByEmail(admindto.getEmail());
        if(adminEmail.isPresent()){
            log.error("admin's email all ready exists {}",admindto);
            throw new InvalidEntityException("User with this email all ready exists ",ErrorCodes.USER_ALL_READY_EXISTS);
        }
        Optional<Admin> adminPhoneNumber= adminRepository.findAdminByPhoneNumber(admindto.getPhoneNumber());
        if(adminPhoneNumber.isPresent()){
            log.error("admin's phone number all ready exists {}",admindto);
            throw new InvalidEntityException("User with this phone number already :"+admindto.getPhoneNumber(),ErrorCodes.USER_ALL_READY_EXISTS);
        }
        admindto.setRole(Roles.ADMIN);
        admindto.setPassword(passwordEncoder.encode(admindto.getPassword()));
        admindto.setStatus(ConnexionStatus.ONLINE);
       /* MongoUser mongoUser=MongoUser.builder()
                //.id(admindto.getId().toString())
                .fullName(admindto.getFirstName()+" "+admindto.getLastName())
                .email(admindto.getEmail())
                .status(admindto.getStatus())
                .build();
        monGoUserRepository.save(mongoUser);*/
        //monGoUserRepository.save(Admindto.toEntity(admindto));
        return Admindto.fromEntity(
                adminRepository.save(
                        Admindto.toEntity(admindto)
                )
        );
    }

    @Override
    public Admindto findById(Integer id) {
        if(id==null){
            log.error("admin id is null !!");
            return null;
        }
        Optional<Admin> admin=adminRepository.findById(id);
        return Optional.of(Admindto.fromEntity(admin.get())).orElseThrow(
                ()-> new EntityNotFoundException("no such admin with this id : "+id+" !!!",
                        ErrorCodes.ADMIN_NOT_FOUND
                )
        ) ;
    }

    @Override
    public Admindto findByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("admin email is null !!");
            return null;
        }
        Optional<Admin> admin=adminRepository.findAdminByEmail(email);
        return Optional.of(Admindto.fromEntity(admin.get())).orElseThrow(
                ()-> new EntityNotFoundException("no such admin with this email : "+email+" !!!",
                        ErrorCodes.ADMIN_NOT_FOUND
                )
        ) ;
    }

    @Override
    public Admindto findByPhoneNumber(String phoneNumber) {
        if(!StringUtils.hasLength(phoneNumber)){
            log.error("admin phone number is null !!");
            return null;
        }
        Optional<Admin> admin=adminRepository.findAdminByPhoneNumber(phoneNumber);
        return Optional.of(Admindto.fromEntity(admin.get())).orElseThrow(
                ()-> new EntityNotFoundException("no such admin with this phone number : "+phoneNumber+" !!!",
                        ErrorCodes.ADMIN_NOT_FOUND
                )
        ) ;
    }

    @Override
    public List<Admindto> findByFirstName(String firstName) {
        if(!StringUtils.hasLength(firstName)){
            log.error("No admins first name provided");
            return null;
        }

        return adminRepository.findAllByFirstName(firstName).stream()
                .map(Admindto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Admindto> findAll() {
        return adminRepository.findAll().stream()
                .map(Admindto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Admindto update(Admindto admindto) {
        if (admindto.getId()==null){
            throw new IllegalArgumentException("admin ID cannot be null");
        }

        Admin existingAdmin= adminRepository.findById(admindto.getId())
                .orElseThrow(
                        ()-> new EntityNotFoundException("no such admin with this id : "+admindto.getId()+" !!!",
                                ErrorCodes.ADMIN_NOT_FOUND)
                );

        existingAdmin.setFirstName(StringUtils.hasLength(admindto.getFirstName())?
                admindto.getFirstName():existingAdmin.getFirstName());

        existingAdmin.setLastName(StringUtils.hasLength(admindto.getLastName())?
                admindto.getLastName() : existingAdmin.getLastName());

        existingAdmin.setPhoneNumber(StringUtils.hasLength(admindto.getPhoneNumber())?
                admindto.getPhoneNumber() : existingAdmin.getPhoneNumber());

        existingAdmin.setPassword(StringUtils.hasLength(admindto.getPassword())?
                admindto.getPassword() : existingAdmin.getPassword());

        Admin updatedAdmin= adminRepository.save(existingAdmin);

        return Admindto.fromEntity(updatedAdmin);
    }

    @Override
    public void deleteById(Integer id) {
        if(id==null){
            log.error("admin id is null !!");
            return;
        }
        adminRepository.deleteById(id);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        if(!StringUtils.hasLength(phoneNumber)){
            log.error("admin phone number is null !!");
            return;
        }
        adminRepository.deleteAdminByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("admin email is null !!");
            return;
        }
        adminRepository.deleteAdminByEmail(email);
    }
}
