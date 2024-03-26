package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.Utils.JwtUtils;
import com.PFA2.EduHousing.controller.api.AdminApi;
import com.PFA2.EduHousing.dto.Admindto;
import com.PFA2.EduHousing.model.Admin;
import com.PFA2.EduHousing.model.ConfirmationToken;
import com.PFA2.EduHousing.model.Student;
import com.PFA2.EduHousing.model.auth.ExtendedUser;
import com.PFA2.EduHousing.repository.AdminRepository;
import com.PFA2.EduHousing.repository.ConfirmationTokenRepository;
import com.PFA2.EduHousing.services.AdminService.AdminService;
import com.PFA2.EduHousing.services.email.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@RestController
public class AdminController implements AdminApi {
    private final AdminService adminService;
    private final JwtUtils jwtUtils;
    private AdminRepository adminRepository;
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public AdminController(
            AdminService adminService,
            JwtUtils jwtUtils,
            AdminRepository adminRepository,
            EmailService emailService,
            ConfirmationTokenRepository confirmationTokenRepository
    ){
        this.adminService=adminService;
        this.jwtUtils=jwtUtils;
        this.adminRepository=adminRepository;
        this.emailService=emailService;
        this.confirmationTokenRepository=confirmationTokenRepository;
    }
    @Override
    public String save(Admindto admindto) {
        admindto.setIsEnabled(false);
        Admindto admin =adminService.save(admindto);
        /*List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority (admin.getRole().toString()));*/
        Optional<Admin> admin1=adminRepository.findAdminByEmail(admin.getEmail());
        ConfirmationToken confirmationToken=new ConfirmationToken(admin1.get());
        confirmationToken.setExpirationDate(Instant.now().plusMillis(1000*60*30));
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage=new SimpleMailMessage();

        mailMessage.setTo(admin1.get().getEmail());
        mailMessage.setSubject("Complete registration");
        // mailMessage.setFrom("adhibi345@gmail.com");
        mailMessage.setText("To confirm your account , please click on the link below and note that the link expires after 30min :"
                +"http://localhost:8081/api"+APP_ROOT+"/confirm-account/admin?token="+confirmationToken.getConfirmationToken()
        );

        emailService.sendEmail(mailMessage);
        return "A verification email is sent to you to the provided email";
    }
    @RequestMapping(value = APP_ROOT+"/confirm-account/admin",method = {RequestMethod.GET,RequestMethod.POST})
    @Operation(summary = "confirm account email... ",description = "the confirmation email is sent to your email .."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "account confirmed successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public String confirmUserAccount(@RequestParam("token") String confirmationToken){
        Optional<ConfirmationToken> token=confirmationTokenRepository.findConfirmationTokenByConfirmationToken(confirmationToken);
        if(token.isEmpty()){
            return "the link is invalid or broken";
        }
        if (token.get().getExpirationDate().isBefore(Instant.now())) {
            return "the link has expired";
        }
        Optional<Admin> admin=adminRepository.findAdminByEmail(token.get().getUser().getEmail());
        admin.get().setIsEnabled(true);
        adminRepository.save(admin.get());
        /*List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority (student.getRole().toString()));*/
        ExtendedUser user = new ExtendedUser(admin.get().getEmail(), admin.get().getPassword(), admin.get().getAuthorities());
        return jwtUtils.generateToken(user);
    }

    @Override
    public Admindto findById(Integer id) {
        return adminService.findById(id);
    }

    @Override
    public Admindto findByEmail(String email) {
        return adminService.findByEmail(email);
    }

    @Override
    public Admindto findByPhoneNumber(String phoneNumber) {
        return adminService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Admindto> findByFirstName(String firstName) {
        return adminService.findByFirstName(firstName);
    }

    @Override
    public List<Admindto> findAll() {
        return adminService.findAll();
    }

    @Override
    public Admindto update(Admindto admindto) {
        return adminService.update(admindto);
    }

    @Override
    public void deleteById(Integer id) {
        adminService.deleteById(id);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        adminService.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        adminService.deleteByEmail(email);
    }
}
