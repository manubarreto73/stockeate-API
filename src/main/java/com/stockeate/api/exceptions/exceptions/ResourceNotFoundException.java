package com.stockeate.api.exceptions.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
