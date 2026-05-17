package br.com.henriquesousa.musicapp.service.exception;

public class CardNotFoundException extends Exception {
   public CardNotFoundException() {
        super("card-not-found");
   }
}
