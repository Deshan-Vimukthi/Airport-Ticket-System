package com.example.airport.exceptionHandling.accessDeniedException;

import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException {
    private final AccessDeniedCode errorCode;

    public AccessDeniedException(String message) {
        super(message);
        this.errorCode = null;
    }

    public AccessDeniedException(String message,AccessDeniedCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }


}
