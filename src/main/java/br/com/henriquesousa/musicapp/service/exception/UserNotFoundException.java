package br.com.henriquesousa.musicapp.service.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("user-not-found");
    }
}
