package com.soumik.movieservice.Exception;

public class MovieNotFoundException extends Exception {

    public MovieNotFoundException() {
        super("Movie not found");
    }

    public MovieNotFoundException(String message) {
        super(message);
    }
}
