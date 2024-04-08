package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.Utils.JwtUtils;
import com.PFA2.EduHousing.controller.api.HomeownerApi;
import com.PFA2.EduHousing.dto.Homeownerdto;
import com.PFA2.EduHousing.model.ConfirmationToken;
import com.PFA2.EduHousing.model.Homeowner;
import com.PFA2.EduHousing.model.auth.ExtendedUser;
import com.PFA2.EduHousing.repository.jpa.ConfirmationTokenRepository;
import com.PFA2.EduHousing.repository.jpa.HomeownerRepository;
import com.PFA2.EduHousing.services.HomeownerService.HomeownerService;
import com.PFA2.EduHousing.services.email.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@RestController
public class HomeownerController implements HomeownerApi {
    private final HomeownerService homeownerService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;
    private final HomeownerRepository homeownerRepository;
    private final JwtUtils jwtUtils;

    public HomeownerController(
            HomeownerService homeownerService,
            ConfirmationTokenRepository confirmationTokenRepository,
            EmailService emailService,
            HomeownerRepository homeownerRepository,
            JwtUtils jwtUtils
    ){
        this.homeownerService=homeownerService;
        this.confirmationTokenRepository=confirmationTokenRepository;
        this.emailService=emailService;
        this.homeownerRepository=homeownerRepository;
        this.jwtUtils=jwtUtils;

    }
    @Override
    public String save(Homeownerdto homeownerdto) {
        homeownerdto.setIsEnabled(false);
        Homeownerdto homeownerdto1=homeownerService.save(homeownerdto);
        Homeowner homeownerModel=Homeownerdto.toEntity(homeownerdto1);
        ConfirmationToken confirmationToken=new ConfirmationToken(homeownerModel);
        confirmationToken.setExpirationDate(Instant.now().plusMillis(1000*60*30));
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage=new SimpleMailMessage();

        mailMessage.setTo(homeownerModel.getEmail());
        mailMessage.setSubject("Complete registration");
        // mailMessage.setFrom("adhibi345@gmail.com");
        mailMessage.setText("To confirm your account , please click on the link below and note that the link expires after 30min :"
                +"http://localhost:8081/api"+APP_ROOT+"/confirm-account/homeowner?token="+confirmationToken.getConfirmationToken()
        );

        emailService.sendEmail(mailMessage);
        return "A verification email is sent to you to the provided email";
    }
    @RequestMapping(value = APP_ROOT+"/confirm-account/homeowner",method = {RequestMethod.GET,RequestMethod.POST})
    @Operation(summary = "confirm account email... ",description = "the confirmation email is sent to your email .."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "account confirmed successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    }) public String confirmUserAccount(@RequestParam("token") String confirmationToken){
        Optional<ConfirmationToken> token=confirmationTokenRepository.findConfirmationTokenByConfirmationToken(confirmationToken);
        if(token.isEmpty()){
            return "the link is invalid or broken";
        }
        if (token.get().getExpirationDate().isBefore(Instant.now())) {
            return "the link has expired";
        }
        Optional<Homeowner> homeowner=homeownerRepository.findHomeownerByEmail(token.get().getUser().getEmail());
        homeowner.get().setIsEnabled(true);
        homeownerRepository.save(homeowner.get());
        /*List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority (student.getRole().toString()));*/
        ExtendedUser user = new ExtendedUser(homeowner.get().getEmail(), homeowner.get().getPassword(), homeowner.get().getAuthorities());
        String accessToken=jwtUtils.generateToken(user);
        return "please click on this link  : <a href=\"http://localhost:3000/getStarted/"+accessToken+"\">click here</a>" + accessToken;
    }

    @Override
    public Homeownerdto findById(Integer homeownerId) {
        return homeownerService.findById(homeownerId);
    }

    @Override
    public Homeownerdto findByEmail(String email) {
        return homeownerService.findByEmail(email);
    }

    @Override
    public List<Homeownerdto> findByFirstName(String firstName) {
        return homeownerService.findByFirstName(firstName);
    }

    @Override
    public List<Homeownerdto> findAll() {
        return homeownerService.findAll();
    }

    @Override
    public Homeownerdto findByPhoneNumber(String phoneNumber) {
        return homeownerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Homeownerdto update(Homeownerdto homeownerdto) {
        return homeownerService.update(homeownerdto);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        homeownerService.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        homeownerService.deleteByEmail(email);
    }

    @Override
    public void deleteById(Integer id) {
        homeownerService.deleteById(id);
    }
}
