package com.PFA2.EduHousing.repository;

import com.PFA2.EduHousing.model.ApplicationFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationFeedbackRepository extends JpaRepository<ApplicationFeedback,Integer> {
    Optional<ApplicationFeedback> findApplicationFeedbackByStudentId(Integer id);
    Optional<ApplicationFeedback> findApplicationFeedbackByHomeownerId(Integer id);
    @Query("select app from ApplicationFeedback app where app.homeowner.firstName=:userName or app.student.firstName=:userName")
    List<ApplicationFeedback> findAllByFirstName(String userName);
}
