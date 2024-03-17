package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.ApplicationFeedbackdto;
import com.PFA2.EduHousing.dto.RentalFeedbackdto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RentalFeedbackValidator {
    public  static List<String> validate(RentalFeedbackdto rentalFeedback){

        List<String> errors=new ArrayList<>();
        if(rentalFeedback==null){
            errors.add("require content for the rental feedback");
            errors.add("require rating for the rental feedback");
        }else {
            if(!StringUtils.hasLength(rentalFeedback.getContent())){
                errors.add("require content for the rental feedback");
            }
            if(rentalFeedback.getRating()==null){
                errors.add("require rating for the rental feedback");
            }
        }

        return errors;
    }
}
