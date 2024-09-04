package com.wrtr.wrtr.core.exceptions;

/**
 * The exception to throw if the post wasn't found in the database
 */
public class PostNotFoundException extends Exception{

    /**
     * Default constructor
     * @param errorMessage Message of the exception
     */
    public PostNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
