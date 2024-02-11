package com.core.gameservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiException extends Exception {

    private Integer errorCode;
    private HttpStatus status;

    public ApiException(String msg, Integer errorCode, HttpStatus status) {
        super(msg);
        this.errorCode = errorCode;
        this.status = status;
    }
}
