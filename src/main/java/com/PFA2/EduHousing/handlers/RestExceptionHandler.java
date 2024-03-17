package com.PFA2.EduHousing.handlers;

import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.InvalidEntityException;
import com.PFA2.EduHousing.exceptions.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception, WebRequest webRequest){

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto= ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDto,notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception, WebRequest webRequest){

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto= ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();

        return new ResponseEntity<>(errorDto,badRequest);
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorDto> handleException(RequestException exception, WebRequest webRequest){

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto= ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();

        return new ResponseEntity<>(errorDto,badRequest);
    }

}
