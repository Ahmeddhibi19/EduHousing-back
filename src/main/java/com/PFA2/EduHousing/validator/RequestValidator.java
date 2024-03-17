package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.Requestdto;
import com.PFA2.EduHousing.model.Status;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RequestValidator {

    public static List<String> validate(Requestdto request){

        List<String> errors = new ArrayList<>();
        if(request==null){
            errors.add("require content");
            errors.add("require acceptance delay");
            errors.add("validation delay must be the end day of the rent");
            errors.add("validation time must be after acceptance time");
            errors.add("acceptance delay must be after validation time ");
            errors.add("acceptance time,validation time,acceptance delay and validation delay must be after start date of the rent");
            errors.add("acceptance time,validation time and acceptance delay must be before end date of the rent");

        }else {
            if(!StringUtils.hasLength(request.getContent())){
                errors.add("require content");
            }
        }

        return  errors;
    }
}
