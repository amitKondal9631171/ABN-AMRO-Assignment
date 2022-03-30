/**
 * DTO to send custom errors to client.
 */

package com.abn.amro.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApiErrorDTO {


    private String message;
    private int errorCode;
    private String endPoint;

    public ApiErrorDTO(int errorCode, String message, String endPoint) {
        this.errorCode = errorCode;
        this.message = message;
        this.endPoint = endPoint;
    }
}