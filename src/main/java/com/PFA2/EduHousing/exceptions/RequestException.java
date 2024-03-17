package com.PFA2.EduHousing.exceptions;

import lombok.Getter;

import java.util.List;

public class RequestException extends RuntimeException{
    @Getter
    private ErrorCodes errorCode;
    @Getter
    List<String> errors;

    public RequestException(String message){super(message);}

    public RequestException(String message, Throwable cause){
        super(message, cause);
    }

    public RequestException(String message,Throwable cause,ErrorCodes errorCode){
        super(message, cause);
        this.errorCode=errorCode;
    }
    public RequestException(String message, ErrorCodes errorCode){
        super(message);
        this.errorCode=errorCode;
    }
    public RequestException(String message, ErrorCodes errorCode,List<String> errors){
        super(message);
        this.errorCode=errorCode;
        this.errors=errors;
    }

}
