package com.lambdaschool.schools.services;

import com.lambdaschool.schools.models.ValidationError;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

public interface HelperFunctions {
    /**
     * Searches to see if the exception has any constraint violations to report
     *
     * @param cause the exception to search
     * @return constraint violations formatted for sending to the client
     */
    public List<ValidationError> getConstraintViolation(Throwable cause);

    public String getNoHandlerFoundExceptionDetail(NoHandlerFoundException nhfe);
}
