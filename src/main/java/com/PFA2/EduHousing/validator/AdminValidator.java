package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.Admindto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdminValidator {
    public static List<String> validate(Admindto admin){
        List<String> errors = new ArrayList<>();

        if (admin==null){
            errors.add("require firstname of the admin");
            errors.add("require lastname of the admin");
            errors.add("require email of the admin");
            errors.add("require password of the admin");
        }else {

            if(!StringUtils.hasLength(admin.getFirstName())){
                errors.add("require firstname of the admin ");
            }
            if(!StringUtils.hasLength(admin.getLastName())){
                errors.add("require lastname of the admin");
            }
            if(!StringUtils.hasLength(admin.getEmail())){
                errors.add("require email of the admin");
            }

            if(!StringUtils.hasLength(admin.getPassword())){
                errors.add("require password of the admin");
            }
        }

        return errors;
    }
}
