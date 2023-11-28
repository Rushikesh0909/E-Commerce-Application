package com.bikkadIt.exception;

public class BadRequest extends  RuntimeException{


    String message;

    public BadRequest(String message) {
        super(message);
        this.message = message;
    }
}
