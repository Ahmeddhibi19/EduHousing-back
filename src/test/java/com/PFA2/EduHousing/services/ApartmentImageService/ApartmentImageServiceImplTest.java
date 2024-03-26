package com.PFA2.EduHousing.services.ApartmentImageService;
import static org.mockito.Mockito.*;

import com.PFA2.EduHousing.Utils.ImageUtils;
import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.ApartmentImage;
import com.PFA2.EduHousing.repository.ApartmentImageRepository;
import com.PFA2.EduHousing.repository.ApartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class ApartmentImageServiceImplTest {


    @Mock
    private ApartmentImageRepository apartmentImageRepository;

    @Mock
    private ApartmentRepository apartmentRepository;

    @InjectMocks
    private ApartmentImageServiceImpl apartmentImageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() throws Exception {
        // Mock apartment and file
        Apartment apartment = new Apartment();
        apartment.setId(1);
        MultipartFile file = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "testdata".getBytes());

        // Mock behavior of repository methods
        when(apartmentRepository.findById(1)).thenReturn(Optional.of(apartment));
        when(apartmentImageRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method to be tested
        String result = apartmentImageService.save(file, 1);

        // Assertions
        assertEquals("file uploaded successfullytest.jpg", result);
        verify(apartmentRepository, times(1)).save(apartment);
        verify(apartmentImageRepository, times(1)).save(any());
    }

    @Test
    void findById() {
        // Mock behavior of repository method
        byte[] imageData = "testdata".getBytes();
        ApartmentImage apartmentImage = new ApartmentImage();
        apartmentImage.setData(imageData);
        when(apartmentImageRepository.findById(1)).thenReturn(Optional.of(apartmentImage));

        // Call the method to be tested
        byte[] result = apartmentImageService.findById(1);

        // Assertions
        assertArrayEquals(imageData, result);
    }

    @Test
    void findAllByApartmentId() {
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteById() {
    }
}