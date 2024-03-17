package com.PFA2.EduHousing.validator;

import com.PFA2.EduHousing.dto.RentalDetailsdto;

import java.util.ArrayList;
import java.util.List;

public class RentalDetailsValidator {
    public static List<String> validate(RentalDetailsdto rentalDetails){
        List<String> errors = new ArrayList<>();

        if(rentalDetails==null){
            errors.add("mouthly amaount cannot be null nor negative");
            errors.add("require start date");
            errors.add("require end date");
            errors.add("end date must be after start date");
        }else {
            if(rentalDetails.getMonthlyAmount()==null || rentalDetails.getMonthlyAmount()<0){
                errors.add("mouthly amaount cannot be null nor negative");
            }
            if(rentalDetails.getStartDate()==null){
                errors.add("require start date");
            }
            if(rentalDetails.getEndDate()==null){
                errors.add("require end date");
            }
            if(rentalDetails.getStartDate()!=null && rentalDetails.getEndDate()!=null){
                if(rentalDetails.getStartDate().after(rentalDetails.getEndDate())){
                    errors.add("end date must be after start date");
                }
            }

        }

        return errors;
    }
}
