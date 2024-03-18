package com.PFA2.EduHousing.exceptions;

public enum ErrorCodes {

    USER_ALL_READY_EXISTS(900),
    USER_NOT_FOUND(901),
    USER_NOT_HOMEOWNER_NEITHER_STUDENT(902),
    STUDENT_NOT_FOUND(1000),
    STUDENT_NOT_VALID(1001),
    LIST_OF_STUDENT_NOT_FOUND(1002),
    HOMEOWNER_NOT_FOUND(1300),
    HOMEOWNER_NOT_VALID(1301),
    ADMIN_NOT_FOUND(1700),
    ADMIN_NOT_VALID(1701),
    APARTMENT_NOT_FOUND(2000),
    APARTMENT_NOT_VALID(2001),
    RENTAL_DETAILS_NOT_FOUND(3000),
    RENTAL_DETAILS_NOT_VALID(3001),
    CUURENT_RENTAL_ALL_READY_EXISTS(3002),
    REQUEST_NOT_FOUND(4000),
    REQUEST_NOT_VALID(4001),
    REQUEST_ACCEPTED_BUT_NO_ACCEPTANCE_DELAY(4002),
    REQUEST_ALL_READY_EXISTS(4003),
    NO_ACCEPTED_REQUEST_FOUND(4004),
    ACCEPTED_REQUEST_ALL_READY_EXISTS(4005),
    COULD_NOT_DELETE_VALIDATED_OR_ACCEPTED_REQUEST(4006),
    REQUEST_VALIDATION_DELAY_MISMATCH(4007),
    CANNOT_REJECT_VALIDATED_OR_ACCEPTED_REQUESTS(4008),
    CITY_NOT_FOUND(5000),
    CITY_NOT_VALID(5001),
    CITY_ALL_READY_EXISTS(5002),
    COLLEGE_NOT_FOUND(6000),
    COLLEGE_NOT_VALID(6001),
    COLLEGE_ALL_READY_EXISTS(6002),
    HOME_IMAGE_NOT_FOUND(7000),
    HOME_IMAGE_NOT_VALID(7001),
    PROFILE_IMAGE_NOT_FOUND(7500),
    PROFILE_IMAGE_NOT_VALID(7501),
    RENTAL_FEEDBACK_NOT_FOUND(8000),
    RENTAL_FEEDBACK_NOT_VALID(8001),
    APPLICATION_FEEDBACK_NOT_FOUND(8500),
    APPLICATION_FEEDBACK_NOT_VALID(8501),
    USER_ALREADY_HAS_APPLICATION_FEEDBACK(8502),
    FAVOURITES_NOT_FOUND(9000),
    FAVOURITES_NOT_VALID(9001),
    Distance_NOT_FOUND(10000),
    Distance_NOT_VALID(10001);



    private int code;

    ErrorCodes(int code){
        this.code=code;
    }

    public int getCode(){
        return  this.code;
    }
}
