package com.PFA2.EduHousing.services.ApplicationFeedbackService;

import com.PFA2.EduHousing.dto.ApplicationFeedbackdto;

import java.util.List;

public interface ApplicationFeedbackService {

    public ApplicationFeedbackdto save(
            ApplicationFeedbackdto applicationFeedbackdto,
            Integer userId
    );
    public ApplicationFeedbackdto findAppFeedbackByUser(Integer userId);

    public List<ApplicationFeedbackdto> findAll();
    public List<ApplicationFeedbackdto> findAllByUserName(String userName);

    public ApplicationFeedbackdto update(ApplicationFeedbackdto applicationFeedbackdto);
    public void deleteById(Integer id);

}
