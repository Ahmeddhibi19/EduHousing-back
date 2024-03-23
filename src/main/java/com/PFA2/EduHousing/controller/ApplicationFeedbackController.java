package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.ApplicationFeedbackApi;
import com.PFA2.EduHousing.dto.ApplicationFeedbackdto;
import com.PFA2.EduHousing.services.ApplicationFeedbackService.ApplicationFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ApplicationFeedbackController implements ApplicationFeedbackApi {
    private final ApplicationFeedbackService applicationFeedbackService;
    @Autowired
    public ApplicationFeedbackController(
            ApplicationFeedbackService applicationFeedbackService
    ){
        this.applicationFeedbackService=applicationFeedbackService;
    }
    @Override
    public ApplicationFeedbackdto save(ApplicationFeedbackdto applicationFeedbackdto, Integer userId) {
        return applicationFeedbackService.save(applicationFeedbackdto,userId);
    }

    @Override
    public ApplicationFeedbackdto findAppFeedbackByUser(Integer userId) {
        return applicationFeedbackService.findAppFeedbackByUser(userId);
    }

    @Override
    public List<ApplicationFeedbackdto> findAll() {
        return applicationFeedbackService.findAll();
    }

    @Override
    public List<ApplicationFeedbackdto> findAllByUserName(String userName) {
        return applicationFeedbackService.findAllByUserName(userName);
    }

    @Override
    public ApplicationFeedbackdto update(ApplicationFeedbackdto applicationFeedbackdto) {
        return applicationFeedbackService.update(applicationFeedbackdto);
    }

    @Override
    public void deleteById(Integer id) {
        applicationFeedbackService.deleteById(id);
    }
}
