package com.soumik.movieservice.Exception;

public class InvalidIdException extends NumberFormatException {
    public InvalidIdException() {
        super("Invalid id format");
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
