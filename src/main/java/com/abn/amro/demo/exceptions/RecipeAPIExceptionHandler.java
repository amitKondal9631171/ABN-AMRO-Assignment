package com.abn.amro.demo.exceptions;


import com.abn.amro.demo.dto.ApiErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * RecipeAPIExceptionHandler is central controller to handle all API exceptions.
 */

@RestControllerAdvice
public class RecipeAPIExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RecipeAPIExceptionHandler.class);
    private static final String COMMON_ERROR_MESSAGE = "Error processing request: {}";
    @Autowired
    private HttpServletRequest servletRequest;


    /**
     * Method handles custom exceptions thrown by the api.
     * @param recipeProcessingException
     * @return
     */
    @ExceptionHandler({RecipeProcessingException.class})
    public ResponseEntity<ApiErrorDTO> apiExceptionHandler(RecipeProcessingException recipeProcessingException) {
        logger.error(COMMON_ERROR_MESSAGE, recipeProcessingException);
        return new ResponseEntity<>(new ApiErrorDTO(recipeProcessingException.getHttpStatus().value(), recipeProcessingException.getMessage(), servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }


    /**
     * Method to handle the invalid data exceptions.
     * @param dataViolationException
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorDTO> dataViolationExceptionHandler(DataIntegrityViolationException dataViolationException) {
        logger.error(COMMON_ERROR_MESSAGE, dataViolationException);
        return new ResponseEntity<>(new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), dataViolationException.getCause().getCause().getMessage(), servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to handle all generic exceptions
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleExceptions(Exception exception) {
        logger.error(COMMON_ERROR_MESSAGE, exception);
        return new ResponseEntity<>(new ApiErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getLocalizedMessage(), servletRequest.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
}



