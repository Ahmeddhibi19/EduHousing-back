package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Studentdto  {

    private  Integer id ;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private Roles role;

    private String address;

    @JsonIgnore
    private ProfileImagedto profileImage;

    @JsonIgnore
    private ApplicationFeedbackdto applicationFeedbackdto;

    @JsonIgnore
    private Set<Favouritesdto> favourites = new HashSet<>();

    @JsonIgnore
    private Set<Requestdto> requests = new HashSet<>();

    @JsonIgnore
    private Set<RentalFeedbackdto> rentalFeedbacks = new HashSet<>();

    @JsonIgnore
    private Collegedto college;

    public static Studentdto fromEntity(Student student){
        if(student == null){
            return null;
        }
        return Studentdto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .password(student.getPassword())
                .phoneNumber(student.getPhoneNumber())
                .role(student.getRole())
                .address(student.getAddress())
                .build();
    }


    public static Student toEntity(Studentdto studentdto){
        if (studentdto==null){
            return null;
        }
        Student student=new Student();
        student.setId(studentdto.getId());
        student.setFirstName(studentdto.getFirstName());
        student.setLastName(studentdto.getLastName());
        student.setEmail(studentdto.getEmail());
        student.setPassword(studentdto.getPassword());
        student.setPhoneNumber(studentdto.getPhoneNumber());
        student.setRole(studentdto.getRole());

        return student;
    }


}
