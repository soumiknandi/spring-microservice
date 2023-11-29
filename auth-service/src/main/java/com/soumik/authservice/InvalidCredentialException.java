package com.soumik.authservice;

public class InvalidCredentialException extends Exception {


    public InvalidCredentialException() {
        super("Invalid EmailId or Password");
    }

    public InvalidCredentialException(String message) {
        super(message);
    }
}
