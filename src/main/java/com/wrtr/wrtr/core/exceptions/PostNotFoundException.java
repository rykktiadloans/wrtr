package com.wrtr.wrtr.core.exceptions;

public class PostNotFoundException extends Exception{
    public PostNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
