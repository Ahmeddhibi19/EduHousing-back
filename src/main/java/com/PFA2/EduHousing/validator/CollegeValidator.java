package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.Collegedto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CollegeValidator {
    public  static List<String> validate(Collegedto college){
        List<String> errors = new ArrayList<>();
         if(college==null){
             errors.add("require name of the college");
             errors.add("require address of the college");
             errors.add("reuire latitude of the college");
             errors.add("reuire longitude of the college");
         }else {
             if(!StringUtils.hasLength(college.getName())){
                 errors.add("require name of the college");
             }
             if(!StringUtils.hasLength(college.getAddress())){
                 errors.add("require address of the college");
             }
             if(college.getLatitude()==null){
                 errors.add("reuire latitude of the college");
             }
             if(college.getLongitude()==null){
                 errors.add("reuire longitude of the college");
             }
         }
         return errors;
    }
}
