/**
 * Custom class to handle runtime exception to send custom messages to client.
 */
package com.abn.amro.demo.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RecipeProcessingException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public RecipeProcessingException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

}