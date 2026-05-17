package br.com.henriquesousa.musicapp.service.exception;

public class UserCardNotFoundException extends Exception {
   public UserCardNotFoundException() {
        super("user-card-not-found");
   }
}
