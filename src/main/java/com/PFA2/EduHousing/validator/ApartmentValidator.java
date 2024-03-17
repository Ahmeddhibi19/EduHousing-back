package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.Apartmentdto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ApartmentValidator {

    public static List<String> validate(Apartmentdto apartment){

        List<String> errors = new ArrayList<>();
        if(apartment==null){
            errors.add("require latitude of the apartment");
            errors.add("require longitude of the apartment");
            errors.add("require address of the apartment");
            errors.add("require type of the apartment (S+1,S+2,S+3,Studio .....)");
        }else {
            if(apartment.getLatitude()==null){
                errors.add("require latitude of the apartment");
            }
            if(apartment.getLongitude()==null){
                errors.add("require longitude of the apartment");
            }
            if(!StringUtils.hasLength(apartment.getAddress())){
                errors.add("require address of the apartment");
            }
            if(apartment.getType()==null){
                errors.add("require type of the apartment (S+1,S+2,S+3,Studio .....)");
            }

        }

        return errors;
    }
}
