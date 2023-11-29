package com.soumik.artistservice.Exception;

public class ArtistNotFoundException extends Exception {


    public ArtistNotFoundException() {
        super("Artist Not found");
    }


    public ArtistNotFoundException(String message) {
        super(message);
    }
}
