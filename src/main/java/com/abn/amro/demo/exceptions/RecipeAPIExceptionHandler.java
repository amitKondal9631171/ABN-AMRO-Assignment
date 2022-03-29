package com.abn.amro.demo.exceptions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnError;
import java.time.LocalTime;


@RestControllerAdvice
public class RecipeAPIExceptionHandler {

   @ExceptionHandler({RecipeProcessingException.class})
    public ResponseEntity<RecipeProcessingException> apiExceptionHandler(RecipeProcessingException recipeProcessingException) {
       return new ResponseEntity<>(recipeProcessingException, recipeProcessingException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    RecipeProcessingException handleExceptions(Exception exception) {
        return new RecipeProcessingException(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}



