package io.github.mmpodkanski.filmroom.exception;

public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException() {
        super();
    }


    public UsernameNotFoundException(String message) {
        super(message);
    }


    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
