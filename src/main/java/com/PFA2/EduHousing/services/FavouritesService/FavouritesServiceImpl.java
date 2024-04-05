package com.PFA2.EduHousing.services.FavouritesService;

import com.PFA2.EduHousing.dto.Favouritesdto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.Favourites;
import com.PFA2.EduHousing.model.Student;
import com.PFA2.EduHousing.repository.jpa.ApartmentRepository;
import com.PFA2.EduHousing.repository.jpa.FavouritesRepository;
import com.PFA2.EduHousing.repository.jpa.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FavouritesServiceImpl implements FavouritesService {
    private final ApartmentRepository apartmentRepository;
    private final StudentRepository studentRepository;
    private final FavouritesRepository favouritesRepository;

    @Autowired
    public FavouritesServiceImpl(
            ApartmentRepository apartmentRepository,
            StudentRepository studentRepository,
            FavouritesRepository favouritesRepository
    ){
        this.apartmentRepository=apartmentRepository;
        this.favouritesRepository=favouritesRepository;
        this.studentRepository=studentRepository;
    }
    @Override
    public Favouritesdto addToFavourites(Favouritesdto favouritesdto,Integer studentId,Integer apartmentId) {
        Student student=studentRepository.findById(studentId).orElseThrow(
                ()->new EntityNotFoundException("no student with this Id :"+studentId,
                        ErrorCodes.STUDENT_NOT_FOUND)
        );
        Apartment apartment = apartmentRepository.findById(apartmentId).orElseThrow(
                ()->new EntityNotFoundException("no apartment with this Id :"+apartmentId,
                        ErrorCodes.APARTMENT_NOT_FOUND)
        );
        Favourites favourites=Favouritesdto.toEntity(favouritesdto);
        favourites.setStudent(student);
        student.getFavouritesSet().add(favourites);
        favourites.setApartment(apartment);
        apartment.getFavouritesSet().add(favourites);
        studentRepository.save(student);
        apartmentRepository.save(apartment);
        return Favouritesdto.fromEntity(favouritesRepository.save(favourites));
    }

    @Override
    public void delete(Integer favouritesId) {
        if(favouritesId==null){
            log.error("favourites id is null");
            return;
        }
        favouritesRepository.deleteById(favouritesId);
    }

    @Override
    public List<Favouritesdto> findByStudentId(Integer studentId) {
        if(studentId==null){
            log.error("student id is null");
            return null;
        }
        Student student=studentRepository.findById(studentId).orElseThrow(
                ()->new EntityNotFoundException("no student with this Id :"+studentId,
                        ErrorCodes.STUDENT_NOT_FOUND)
        );
        return favouritesRepository.findAllByStudentId(studentId).stream()
                .map(Favouritesdto::fromEntity)
                .collect(Collectors.toList());
    }
}
