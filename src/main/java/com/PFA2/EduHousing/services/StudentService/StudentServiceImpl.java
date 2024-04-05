package com.PFA2.EduHousing.services.StudentService;

import com.PFA2.EduHousing.dto.Studentdto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.model.College;
import com.PFA2.EduHousing.model.ConnexionStatus;
import com.PFA2.EduHousing.model.Roles;
import com.PFA2.EduHousing.model.Student;
import com.PFA2.EduHousing.model.chat.MongoUser;
import com.PFA2.EduHousing.repository.jpa.CollegeRepository;
import com.PFA2.EduHousing.repository.mongo.MonGoUserRepository;
import com.PFA2.EduHousing.repository.jpa.StudentRepository;
import com.PFA2.EduHousing.validator.StudentValidator;
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
public class StudentServiceImpl implements StudentService {
    private final CollegeRepository collegeRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final MonGoUserRepository monGoUserRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository , CollegeRepository collegeRepository,PasswordEncoder passwordEncoder,MonGoUserRepository monGoUserRepository){
        this.studentRepository=studentRepository;
        this.collegeRepository=collegeRepository;
        this.passwordEncoder=passwordEncoder;
        this.monGoUserRepository=monGoUserRepository;
    }
    @Override
    public Studentdto save(Studentdto studentdto,Integer collegeId) {

        List<String> errors= StudentValidator.validate(studentdto);

        if(!errors.isEmpty()){
            log.error("Student is not valid {}",studentdto);
            throw new InvalidEntityException("Student is not valid", ErrorCodes.STUDENT_NOT_VALID,errors);
        }
        College college=collegeRepository.findById(collegeId).orElseThrow(
                ()->new EntityNotFoundException("college with this id :"+collegeId+" is not found",ErrorCodes.COLLEGE_NOT_FOUND)
        );

        //check if student email exists in the dataBase ( email is unique )
        Optional<Student> studentEmail=studentRepository.findStudentByEmail(studentdto.getEmail());
        if(studentEmail.isPresent()){
            log.error("Student's email all ready exists {}",studentdto);
            throw new InvalidEntityException("User all ready exists with this email",ErrorCodes.USER_ALL_READY_EXISTS);
        }

        //Check if student phone number exists in the data Base (phone number is unique)
        Optional<Student> studentPhoneNumber=studentRepository.findStudentByPhoneNumber(studentdto.getPhoneNumber());
        if(studentPhoneNumber.isPresent()){
            log.error("Student's phone number all ready exists {}",studentdto);
            throw new InvalidEntityException("User all ready exists with this phone number",ErrorCodes.USER_ALL_READY_EXISTS);
        }
        studentdto.setRole(Roles.STUDENT);
        studentdto.setPassword(passwordEncoder.encode(studentdto.getPassword()));
        studentdto.setStatus(ConnexionStatus.ONLINE);
        Student newStudent=Studentdto.toEntity(studentdto);
        newStudent.setCollege(college);
        MongoUser mongoUser=MongoUser.builder()
                //.id(newStudent.getId().toString())
                .fullName(newStudent.getFirstName()+" "+newStudent.getLastName())
                .status(newStudent.getStatus())
                .build();
        monGoUserRepository.save(mongoUser);
        return Studentdto.fromEntity(studentRepository.save(newStudent));
    }

    @Override
    public Studentdto findById(Integer id) {

        if(id==null){
            log.error("Student id is null !!");
            return null;
        }
        Optional<Student> student = studentRepository.findById(id);
        return Optional.of(Studentdto.fromEntity(student.get())).orElseThrow(()-> new EntityNotFoundException(
                "no student with this id : "+id+" found in the DataBase",
                ErrorCodes.STUDENT_NOT_FOUND
                )
        );
    }

    @Override
    public Studentdto findByEmail(String email) {

        if(!StringUtils.hasLength(email)){
            log.error("Student email is null !!");
            return null;
        }
        Optional<Student> student = studentRepository.findStudentByEmail(email);
        return Optional.of(Studentdto.fromEntity(student.get())).orElseThrow(()-> new EntityNotFoundException(
                        "no student with this id : "+email+" found in the DataBase",
                        ErrorCodes.STUDENT_NOT_FOUND
                )
        );
    }

    @Override
    public List<Studentdto> findByFirstName(String firstName) {

        if(!StringUtils.hasLength(firstName)){
            log.error("Students first name is null !!");
            return null;
        }
        return studentRepository.findAllByFirstName(firstName).stream()
                .map(Studentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Studentdto findByPhoneNumber(String phoneNumber) {
        if(!StringUtils.hasLength(phoneNumber)){
            log.error("Student email is null !!");
            return null;
        }
        Optional<Student> student = studentRepository.findStudentByPhoneNumber(phoneNumber);
        return Optional.of(Studentdto.fromEntity(student.get())).orElseThrow(()-> new EntityNotFoundException(
                        "no student with this id : "+phoneNumber+" found in the DataBase",
                        ErrorCodes.STUDENT_NOT_FOUND
                )
        );
    }

    @Override
    public Studentdto update(Studentdto studentdto) {

        if(studentdto.getId()==null){
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Student existingStudent =studentRepository.findById(studentdto.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("no student with this id : "+studentdto.getId()+" found in the DataBase",
                        ErrorCodes.STUDENT_NOT_FOUND)
                );

        existingStudent.setFirstName(StringUtils.hasLength(studentdto.getFirstName()) ?
                studentdto.getFirstName() : existingStudent.getFirstName());

        existingStudent.setLastName(StringUtils.hasLength(studentdto.getLastName()) ?
                studentdto.getLastName() : existingStudent.getLastName());

/*        existingStudent.setPassword(StringUtils.hasLength(studentdto.getPassword()) ?
                studentdto.getPassword() : existingStudent.getPassword());*/

        existingStudent.setPhoneNumber(StringUtils.hasLength(studentdto.getPhoneNumber()) ?
                studentdto.getPhoneNumber() : existingStudent.getPhoneNumber());

        existingStudent.setAddress(StringUtils.hasLength(studentdto.getAddress()) ?
                studentdto.getAddress() : existingStudent.getAddress());


        Student updatedStudent = studentRepository.save(existingStudent);
        return Studentdto.fromEntity(updatedStudent);

    }

    @Override
    public void deleteById(Integer id) {
        if(id==null){
            log.error("Student id is null !!");
            return;
        }
        studentRepository.deleteById(id);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        if(!StringUtils.hasLength(phoneNumber)){
            log.error("Student phone number is null !!");
            return;
        }
        studentRepository.deleteStudentByPhoneNumber(phoneNumber);

    }

    @Override
    public void deleteByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("Student email is null !!");
            return;
        }
        studentRepository.deleteStudentByEmail(email);
    }

    @Override
    public List<Studentdto> findAll() {
        return studentRepository.findAll().stream()
                .map(Studentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Studentdto> findAllByCollegeId(Integer collegeId) {
        return studentRepository.findAllByCollegeId(collegeId).stream()
                .map(Studentdto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Studentdto updateCollege(Integer studentId,Integer collegeId) {
        if (collegeId==null){
            log.error("new college id is null");
            return null;
        }
        if (studentId==null){
            log.error("student id is null");
            return null;
        }
        College college=collegeRepository.findById(collegeId).orElseThrow(
                ()->new EntityNotFoundException("no college with this id :"+collegeId+"is found",ErrorCodes.COLLEGE_NOT_FOUND)
        );
        Student student=studentRepository.findById(studentId).orElseThrow(
                ()->new EntityNotFoundException("no student with this id :"+studentId+"is found",ErrorCodes.STUDENT_NOT_FOUND)
        );
        student.setCollege(college);
        return Studentdto.fromEntity(
                studentRepository.save(student)
        );
    }
}
