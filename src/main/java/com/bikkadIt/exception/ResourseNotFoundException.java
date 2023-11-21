package com.bikkadIt.exception;


public class ResourseNotFoundException extends RuntimeException {

    String message;

    public ResourseNotFoundException(String message){
        super(message);
        this.message=message;
    }
}
