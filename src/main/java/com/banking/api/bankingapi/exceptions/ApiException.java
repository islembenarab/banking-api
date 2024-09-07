package com.banking.api.bankingapi.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonIgnoreProperties(value = {"suppressed", "localizedMessage", "stackTrace"}, allowGetters = true)
public class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
    public ApiException (String s, HttpStatus httpStatus) {
        this.message=s;
        this.httpStatus=httpStatus;
    }
}
