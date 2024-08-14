package com.wrtr.wrtr.core.storage;

/**
 * Exceptions that StorageService can use
 */
public class StorageException extends RuntimeException{
    /**
     * Constructor that uses only a message
     * @param message Exception message
     */
    public StorageException(String message){
        super(message);
    }

    /**
     * Constructor that uses a message and a throwable
     * @param message Exception message
     * @param cause Throwable cause
     */
    public StorageException(String message, Throwable cause){
        super(message, cause);
    }
}
