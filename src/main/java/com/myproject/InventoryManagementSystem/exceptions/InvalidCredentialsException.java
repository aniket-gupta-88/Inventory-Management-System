package com.myproject.InventoryManagementSystem.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message){
        super(message);
    }
}
