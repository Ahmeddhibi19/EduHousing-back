package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.Studentdto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StudentValidator {
    public static List<String> validate(Studentdto student){
        List<String> errors = new ArrayList<>();

        if (student==null){
            errors.add("require firstname of the student");
            errors.add("require lastname of the student");
            errors.add("require email of the student");
            errors.add("require password of the student");
            errors.add("require address of the student");
        }else {

            if(!StringUtils.hasLength(student.getFirstName())){
                errors.add("require firstname of the student ");
            }
            if(!StringUtils.hasLength(student.getLastName())){
                errors.add("require lastname of the student");
            }
            if(!StringUtils.hasLength(student.getEmail())){
                errors.add("require email of the student");
            }

            if(!StringUtils.hasLength(student.getPassword())){
                errors.add("require password of the student");
            }
            if(!StringUtils.hasLength(student.getAddress())){
                errors.add("require address of the student");
            }
        }

        return errors;
    }
}
