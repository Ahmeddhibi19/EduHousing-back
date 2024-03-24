package com.PFA2.EduHousing.services.AdminService;

import com.PFA2.EduHousing.dto.Admindto;
import com.PFA2.EduHousing.model.Roles;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;

    @Test
    public void shouldSaveAdminWithSuccess(){
        Admindto expectedadmindto=Admindto.builder()
                .id(12)
                .firstName("firstTes")
                .lastName("lastTest")
                .email("email2@email.com")
                .password("passwordTes")
                .phoneNumber("phoneNumberTest1")
                .role(Roles.ADMIN)
                .isActivated(true)
                .build();
        Admindto savedAdmin=adminService.save(expectedadmindto);
        assertNotNull(savedAdmin);
        assertNotNull(savedAdmin.getId());
        assertEquals(expectedadmindto,savedAdmin);
    }


    @Test
    void findById() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByPhoneNumber() {
    }

    @Test
    void findByFirstName() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
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