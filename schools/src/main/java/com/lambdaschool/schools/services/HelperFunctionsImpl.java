package com.lambdaschool.schools.services;

import com.lambdaschool.schools.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions {
    @Override
    public List<ValidationError> getConstraintViolation(Throwable cause) {
        while ((cause != null) && !(cause instanceof ConstraintViolationException || cause instanceof MethodArgumentNotValidException)) {
            cause = cause.getCause();
        }

        List<ValidationError> validationErrors = new ArrayList<>();

        if (cause != null) {
            if (cause instanceof  ConstraintViolationException) {
                ConstraintViolationException ex = (ConstraintViolationException) cause;
                ValidationError validationError = new ValidationError();
                validationError.setCode(ex.getMessage());
                validationError.setMessage(ex.getConstraintName());
                validationErrors.add(validationError);
            } else {
                MethodArgumentNotValidException ex = (MethodArgumentNotValidException) cause;
                List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
                for (FieldError error : fieldErrors) {
                    ValidationError validationError = new ValidationError();
                    validationError.setMessage(error.getDefaultMessage());
                    validationError.setCode(error.getField());
                    validationErrors.add(validationError);
                }
            }
        }

        return  validationErrors;
    }

    @Override
    public String getNoHandlerFoundExceptionDetail(NoHandlerFoundException nhfe) {
        return "No handler found for " + nhfe.getHttpMethod() + " " + nhfe.getRequestURL();
    }
}
