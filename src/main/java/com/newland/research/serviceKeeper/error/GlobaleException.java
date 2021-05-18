package com.newland.research.serviceKeeper.error;

import lombok.Data;

@Data
public class GlobaleException extends Exception{
    private Integer code ;
    private String message;

    public GlobaleException(){}

    public GlobaleException(Integer code, String message) {
        super(message);;
        this.code = code;
        this.message = message;
    }
}