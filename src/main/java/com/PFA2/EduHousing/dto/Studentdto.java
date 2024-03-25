package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.Utils.ImageUtils;
import com.PFA2.EduHousing.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.FileOutputStream;
import java.sql.Array;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    private Boolean isEnabled;

    private byte[] profileImage1;
   // private FileOutputStream image;
    private ProfileImagedto profileImage;


    private ApplicationFeedbackdto applicationFeedbackdto;


    private Set<Favouritesdto> favourites = new HashSet<>();


    private Set<Requestdto> requests = new HashSet<>();


    private Set<RentalFeedbackdto> rentalFeedbacks = new HashSet<>();


    private Collegedto college;

    public static Studentdto fromEntity(Student student){
        if(student == null){
            return null;
        }
        byte[] imageData=null;
        if (student.getProfileImage()!=null&&student.getProfileImage().getData()!=null){
            imageData=ImageUtils.decompressImage(student.getProfileImage().getData());
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
                .isEnabled(student.getIsEnabled())
                //.image()
                .profileImage1(imageData)
                /*.profileImage(
                        student.getProfileImage()!=null?
                        ProfileImagedto.builder()
                                .id(student.getProfileImage().getId())
                                .data(*//*Base64.getDecoder().decode(*//*student.getProfileImage().getData())
                                .build():null
                        )*/
                .applicationFeedbackdto(
                        student.getApplicationFeedback()!=null?
                                ApplicationFeedbackdto.builder()
                                        .id(student.getApplicationFeedback().getId())
                                        .content(student.getApplicationFeedback().getContent())
                                        .rating(student.getApplicationFeedback().getRating())
                                        .build():null
                )
                .favourites(
                        student.getFavouritesSet()!=null?
                                student.getFavouritesSet().stream()
                                        .map(Favouritesdto::fromEntity)
                                        .collect(Collectors.toSet()):null
                )
                .requests(
                        student.getRequestSet()!=null?
                                student.getRequestSet().stream()
                                        .map(Requestdto::fromEntity)
                                        .collect(Collectors.toSet()) : null
                )
                .rentalFeedbacks(
                        student.getRentalFeedbackSet()!=null?
                                student.getRentalFeedbackSet().stream()
                                        .map(RentalFeedbackdto::fromEntity)
                                        .collect(Collectors.toSet()) : null
                )
                .college(Collegedto.builder()
                        .id(student.getCollege().getId())
                        .address(student.getCollege().getAddress())
                        .name(student.getCollege().getName())
                        .build())
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
        student.setAddress(studentdto.getAddress());
        student.setIsEnabled(studentdto.getIsEnabled());
        student.setProfileImage(
                studentdto.getProfileImage()!=null?
                        ProfileImagedto.toEntity(studentdto.getProfileImage()):null
        );
        student.setApplicationFeedback(
                studentdto.getApplicationFeedbackdto()!=null?
                        ApplicationFeedbackdto.toEntity(studentdto.getApplicationFeedbackdto()):null
        );
        student.setCollege(Collegedto.toEntity(studentdto.getCollege()));

        return student;
    }


}
