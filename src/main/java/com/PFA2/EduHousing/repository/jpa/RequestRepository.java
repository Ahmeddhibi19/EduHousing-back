package com.PFA2.EduHousing.repository.jpa;

import com.PFA2.EduHousing.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {

    public List<Request> findAllByStudentId(Integer studentId);
    @Query("select r from Request r where r.student.id=:studentId and r.rentalDetails.id=:rentalDetailsId")
    public Optional<Request> getRequestByStudentIdAndRentalDetailsId(Integer studentId, Integer rentalDetailsId);


    public List<Request> findAllByRentalDetailsId(Integer rentalDetailsId);
    @Query("select r from Request r where r.status='VALIDATED' and r.rentalDetails.id=:rentalDetailsId")
    public Optional<Request> getValidatedRequestByRentalDetailsId(Integer rentalDetailsId);

   /* @Query("select r from Request r where r.status='VALIDATED' and r.rentalDetails.id=:rentalDetailsId and r.rentalDetails.isCurrent=true")
    public Request getValidatedRequestByCurrentRentalDetailsId(Integer rentalDetailsId);*/

    @Query("select r from Request r where r.status='VALIDATED' and r.student.id=:studentID")
    public List<Request> getAllValidatedRequestsByStudentId(Integer studentID);

   /* Optional<Request> findByStudentIdAndRentalDetailsId(Integer studentId, Integer rentalDetailsId);*/
    @Query("select r from Request r where r.status='ACCEPTED' AND r.rentalDetails.id=:rentalDetails")
    Optional<Request> getRequestsByAcceptanceAndRentalDetailsId(Integer rentalDetails);
}
