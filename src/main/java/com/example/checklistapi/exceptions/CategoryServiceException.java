package com.example.checklistapi.exceptions;

public class CategoryServiceException extends RuntimeException{
    public CategoryServiceException(String message) {
        super(message);
    }
}
