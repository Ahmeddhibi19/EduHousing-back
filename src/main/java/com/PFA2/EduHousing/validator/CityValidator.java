package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.Citydto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CityValidator {
    public static List<String> validate(Citydto city){
        List<String> errors = new ArrayList<>();

        if(city==null){
            errors.add("require name of the city");
            errors.add("require postal code of the city");

        }else {
            if(!StringUtils.hasLength(city.getName())){
                errors.add("require name of the city");
            }
            if(!StringUtils.hasLength(city.getPostalCode())){
                errors.add("require postal code of the city");
            }
        }
        return errors;
    }
}
