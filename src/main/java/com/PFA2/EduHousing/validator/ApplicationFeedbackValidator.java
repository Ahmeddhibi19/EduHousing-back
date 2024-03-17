package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.ApplicationFeedbackdto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFeedbackValidator {
    public  static List<String> validate(ApplicationFeedbackdto applicationFeedback){

        List<String> errors=new ArrayList<>();
        if(applicationFeedback==null){
            errors.add("require content for the application's feedback");
            errors.add("require rating for the application's feedback");
        }else {
            if(!StringUtils.hasLength(applicationFeedback.getContent())){
                errors.add("require content for the application's feedback");
            }
            if(applicationFeedback.getRating()==null){
                errors.add("require rating for the application's feedback");
            }
        }

        return errors;
    }
}
