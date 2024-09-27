package io.github.marcelobaldi.ApiRestJwt.exception;

import io.github.marcelobaldi.ApiRestJwt.dto.exception.ErrorBeanValidationDTO;
import io.github.marcelobaldi.ApiRestJwt.dto.exception.ErrorResponseDTO;
import io.github.marcelobaldi.ApiRestJwt.exception.exceptions.BusinessRulesException;
import io.github.marcelobaldi.ApiRestJwt.exception.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class SeveralExceptionHandler {
     @ExceptionHandler(BusinessRulesException.class)
    public ErrorResponseDTO handlerBusinessRulesException(Exception ex){
        String msg    = ex.getMessage();
        Number code   = HttpStatus.BAD_REQUEST.value();
        String status = HttpStatus.BAD_REQUEST.getReasonPhrase();
        return new ErrorResponseDTO(msg, code, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponseDTO handlerNotFoundException(Exception ex){
        String msg    = ex.getMessage();
        Number code   = HttpStatus.NOT_FOUND.value();
        String status = HttpStatus.NOT_FOUND.getReasonPhrase();
        return new ErrorResponseDTO(msg, code, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponseDTO notRead(HttpMessageNotReadableException ex) {

        String msg    = "Dados inválidos. " + ex.getMessage();
        Number code   = HttpStatus.BAD_REQUEST.value();
        String status = HttpStatus.BAD_REQUEST.getReasonPhrase();
        return new ErrorResponseDTO(msg, code, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Optional<?> beanValidation(MethodArgumentNotValidException ex) {

        List<ErrorBeanValidationDTO> errorBean = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
           String msg            = error.getDefaultMessage();
           String field          = error.getField();
           Optional<?> parameter = Optional.ofNullable(error.getRejectedValue());

           errorBean.add(new ErrorBeanValidationDTO(msg, field, parameter));
        });

        String msg    = "Dados inválidos.";
        Number code   = HttpStatus.BAD_REQUEST.value();
        String status = HttpStatus.BAD_REQUEST.getReasonPhrase();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(msg, code, status, errorBean);
        return Optional.of(errorResponse);
    }
}
