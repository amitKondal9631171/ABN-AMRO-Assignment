package com.abn.amro.demo.exceptions;


import com.abn.amro.demo.dto.ApiErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RecipeAPIExceptionHandler is central controller to handle all API exceptions.
 */

@RestControllerAdvice
public class RecipeAPIExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RecipeAPIExceptionHandler.class);

    @Autowired
    private HttpServletRequest servletRequest;

    /**
     * Method handles custom exceptions thrown by the api.
     *
     * @param recipeProcessingException contains api processing exception details
     * @return exception details with http status
     */
    @ExceptionHandler({RecipeProcessingException.class})
    public ResponseEntity<ApiErrorDTO> apiExceptionHandler(RecipeProcessingException recipeProcessingException) {
        logger.error("Request processing exception: ", recipeProcessingException);
        return new ResponseEntity<>(new ApiErrorDTO(recipeProcessingException.getHttpStatus().value(), recipeProcessingException.getMessage(), servletRequest.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    /**
     * Method to handle request data validations
     *
     * @param requestException contains invalid request data exceptions
     * @return exception details with http status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> requestErrorHandler(MethodArgumentNotValidException requestException) {
        List<org.springframework.validation.FieldError> fieldErrors = requestException.getBindingResult().getFieldErrors();
        List<String> requestErrors = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        logger.error("Exception: ", requestException);
        return new ResponseEntity<>(new ApiErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), requestErrors.toString(), servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to handle all generic exceptions
     *
     * @param serviceExceptions contains service exceptions
     * @return exception detail with http status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> exceptionHandler(Exception serviceExceptions) {
        logger.error("Exception: ", serviceExceptions);
        return new ResponseEntity<>(new ApiErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), serviceExceptions.getLocalizedMessage(), servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
}



