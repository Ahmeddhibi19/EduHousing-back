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

        }else {
            if(!StringUtils.hasLength(request.getContent())){
                errors.add("require content");
            }
        }

        return  errors;
    }
}
