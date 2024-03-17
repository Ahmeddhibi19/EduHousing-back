package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.Admindto;
import com.PFA2.EduHousing.dto.Homeownerdto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeownerValidator {
    public static List<String> validate(Homeownerdto homeowner){
        List<String> errors = new ArrayList<>();

        if (homeowner==null){
            errors.add("require firstname of the homeowner");
            errors.add("require lastname of the homeowner");
            errors.add("require email of the homeowner");
            errors.add("require password of the homeowner");
            errors.add("require address of the homeowner");
        }else {

            if(!StringUtils.hasLength(homeowner.getFirstName())){
                errors.add("require firstname of the homeowner ");
            }
            if(!StringUtils.hasLength(homeowner.getLastName())){
                errors.add("require lastname of the homeowner");
            }
            if(!StringUtils.hasLength(homeowner.getEmail())){
                errors.add("require email of the homeowner");
            }

            if(!StringUtils.hasLength(homeowner.getPassword())){
                errors.add("require password of the homeowner");
            }
            if(!StringUtils.hasLength(homeowner.getAddress())){
                errors.add("require address of the homeowner");
            }
        }

        return errors;
    }
}
