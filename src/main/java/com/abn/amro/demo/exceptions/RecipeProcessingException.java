package com.abn.amro.demo.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class RecipeProcessingException extends RuntimeException {

    private String errorMessage;
    private HttpStatus httpStatus;

    public RecipeProcessingException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}