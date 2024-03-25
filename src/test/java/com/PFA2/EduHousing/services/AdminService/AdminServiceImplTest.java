package com.PFA2.EduHousing.services.AdminService;

import com.PFA2.EduHousing.dto.Admindto;
import com.PFA2.EduHousing.model.Roles;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;

    @Test
    public void shouldSaveAdminWithSuccess(){
        Admindto adminWillBeSaved =Admindto.builder()
                //.id(30)
                .firstName("firstTes")
                .lastName("lastTest")
                .email("email2@email.com")
                .password("passwordTes")
                .phoneNumber("phoneNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        Admindto expectedadmindto=Admindto.builder()
                .id(32)
                .firstName("firstTes")
                .lastName("lastTest")
                .email("email2@email.com")
                //.password("passwordTes")
                .phoneNumber("phoneNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        Admindto savedAdmin=adminService.save(adminWillBeSaved);
        assertNotNull(savedAdmin);
        assertNotNull(savedAdmin.getId());
        assertEquals(expectedadmindto,savedAdmin);
    }


    @Test
    void findById() {

        Admindto expectedadmindto=Admindto.builder()
                .id(32)
                .firstName("firstTes")
                .lastName("lastTest")
                .email("email2@email.com")
                //.password("passwordTes")
                .phoneNumber("phoneNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        Admindto returnedAdmin=adminService.findById(32);
        assertNotNull(returnedAdmin);
        assertNotNull(returnedAdmin.getId());
        assertEquals(expectedadmindto,returnedAdmin);
    }

    @Test
    void findByEmail() {
        Admindto expectedadmindto=Admindto.builder()
                .id(32)
                .firstName("firstTes")
                .lastName("lastTest")
                .email("email2@email.com")
                //.password("passwordTes")
                .phoneNumber("phoneNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        Admindto returnedAdmin=adminService.findByEmail("email2@email.com");
        assertNotNull(returnedAdmin);
        assertNotNull(returnedAdmin.getId());
        assertEquals(expectedadmindto,returnedAdmin);
    }

    @Test
    void findByPhoneNumber() {
        Admindto expectedadmindto=Admindto.builder()
                .id(32)
                .firstName("firstTes")
                .lastName("lastTest")
                .email("email2@email.com")
                //.password("passwordTes")
                .phoneNumber("phoneNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        Admindto returnedAdmin=adminService.findByPhoneNumber("phoneNumberTest1");
        assertNotNull(returnedAdmin);
        assertNotNull(returnedAdmin.getId());
        assertEquals(expectedadmindto,returnedAdmin);
    }

    @Test
    void findByFirstName() {
        Admindto expectedadmindto=Admindto.builder()
                .id(32)
                .firstName("firstTes")
                .lastName("lastTest")
                .email("email2@email.com")
                //.password("passwordTes")
                .phoneNumber("phoneNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        List<Admindto> admindtoList=new ArrayList<>();
        admindtoList.add(expectedadmindto);
        List<Admindto> returnedList=adminService.findByFirstName("firstTes");
        assertNotNull(returnedList);
        assertNotNull(returnedList.stream().map(admindto -> admindto.getId()!=null).collect(Collectors.toList()));
        assertEquals(admindtoList,returnedList);
    }

    @Test
    void findAll() {
        Admindto expectedadmindto=Admindto.builder()
                .id(32)
                .firstName("firstTes")
                .lastName("lastTest")
                .email("email2@email.com")
                //.password("passwordTes")
                .phoneNumber("phoneNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        List<Admindto> admindtoList=new ArrayList<>();
        admindtoList.add(expectedadmindto);
        List<Admindto> returnedList=adminService.findAll();
        assertNotNull(returnedList);
        assertNotNull(returnedList.stream().map(admindto -> admindto.getId()!=null).collect(Collectors.toList()));
        assertEquals(admindtoList,returnedList);
    }

    @Test
    void update() {
        Admindto adminToUpdate =Admindto.builder()
                .id(32)
                .firstName("ertf")
                .lastName("lavsdfdstTest")
                .email("email2@email.com")
                .password("passwordTes")
                .phoneNumber("phoncvccvcveNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        Admindto expectedadmindto=Admindto.builder()
                .id(32)
                .firstName("ertf")
                .lastName("lavsdfdstTest")
                .email("email2@email.com")
                //.password("passwordTes")
                .phoneNumber("phoncvccvcveNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        Admindto updatedAdmin=adminService.update(adminToUpdate);
        assertNotNull(updatedAdmin);
        assertNotNull(updatedAdmin.getId());
        assertEquals(expectedadmindto,updatedAdmin);
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteByPhoneNumber() {
    }

    @Test
    void deleteByEmail() {
    }
}