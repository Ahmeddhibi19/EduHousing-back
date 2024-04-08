package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.Utils.JwtUtils;
import com.PFA2.EduHousing.controller.api.StudentApi;
import com.PFA2.EduHousing.dto.Studentdto;
import com.PFA2.EduHousing.model.ConfirmationToken;
import com.PFA2.EduHousing.model.Student;
import com.PFA2.EduHousing.model.auth.ExtendedUser;
import com.PFA2.EduHousing.repository.jpa.ConfirmationTokenRepository;
import com.PFA2.EduHousing.repository.jpa.StudentRepository;
import com.PFA2.EduHousing.services.StudentService.StudentService;
import com.PFA2.EduHousing.services.UserService.UserService;
import com.PFA2.EduHousing.services.email.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentController implements StudentApi {

    private final StudentService studentService;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final StudentRepository studentRepository;
    private final UserService userService;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    public StudentController(
            StudentService studentService,
            JwtUtils jwtUtils,
            ConfirmationTokenRepository confirmationTokenRepository,
            EmailService emailService,
            StudentRepository studentRepository,
            UserService userService
    ){
        this.studentService=studentService;
        this.jwtUtils=jwtUtils;
        this.confirmationTokenRepository=confirmationTokenRepository;
        this.emailService=emailService;
        this.studentRepository=studentRepository;
        this.userService=userService;
    }

    @Override
    public String save(Studentdto studentdto, Integer collegeId) {
        studentdto.setIsEnabled(false);
        Studentdto student=studentService.save(studentdto,collegeId);
        /*List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority (student.getRole().toString()));
        ExtendedUser user = new ExtendedUser(student.getEmail(), student.getPassword(), authorities);*/



        Student studentModel=Studentdto.toEntity(student);
        ConfirmationToken confirmationToken = new ConfirmationToken(studentModel);
        confirmationToken.setExpirationDate(Instant.now().plusMillis(1000*60*30));
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage=new SimpleMailMessage();

        mailMessage.setTo(studentModel.getEmail());
        mailMessage.setSubject("Complete registration");
       // mailMessage.setFrom("adhibi345@gmail.com");
        mailMessage.setText("To confirm your account , please click on the link below and note that the link expires after 30min :"
        +"http://localhost:8081/api"+APP_ROOT+"/confirm-account/student?token="+confirmationToken.getConfirmationToken()
                );

        emailService.sendEmail(mailMessage);
        return "A verification email is sent to you to the provided email";
    }
    @RequestMapping(value = APP_ROOT+"/confirm-account/student",method = {RequestMethod.GET,RequestMethod.POST})
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
        Optional<Student> student=studentRepository.findStudentByEmail(token.get().getUser().getEmail());
        student.get().setIsEnabled(true);
        studentRepository.save(student.get());
        /*List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority (student.getRole().toString()));*/
        ExtendedUser user = new ExtendedUser(student.get().getEmail(), student.get().getPassword(), student.get().getAuthorities());
        String accessToken=jwtUtils.generateToken(user);
        return "please click on this link  : <a href=\"http://localhost:3000/getStarted/"+accessToken+"\">click here</a>" + accessToken;
    }

    @Override
    public Studentdto findById(Integer id) {
        return studentService.findById(id);
    }

    @Override
    public Studentdto findByEmail(String email) {
        return studentService.findByEmail(email);
    }

    @Override
    public List<Studentdto> findByFirstName(String firstName) {
        return studentService.findByFirstName(firstName);
    }

    @Override
    public Studentdto findByPhoneNumber(String phoneNumber) {
        return studentService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Studentdto update(Studentdto studentdto) {
        return studentService.update(studentdto);
    }

    @Override
    public Studentdto updateCollege(Integer studentId, Integer collegeId) {
        return studentService.updateCollege(studentId,collegeId);
    }

    @Override
    public void deleteById(Integer id) {
        studentService.deleteById(id);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        studentService.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        studentService.deleteByEmail(email);
    }

    @Override
    public List<Studentdto> findAll() {
        return studentService.findAll();
    }

    @Override
    public List<Studentdto> findAllByCollegeId(Integer collegeId) {
        return studentService.findAllByCollegeId(collegeId);
    }

}
