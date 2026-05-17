package br.com.henriquesousa.musicapp.dto;

import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.entity.UserCard;

public class FactoryDTO {
    public static User newDtoToEntity(NewUserDTO newUserDTO) {
        return new User(
                newUserDTO.getName(),
                newUserDTO.getUserName()
                );
    }
    
    public static User existingDtoToEntity(ExistingUserDTO existingUserDTO) {
        return new User(
                existingUserDTO.getUuid(),
                existingUserDTO.getName(),
                existingUserDTO.getUserName()
                );
    }

    public static ExistingUserDTO entityToDTO(User user) {
        return new ExistingUserDTO(
                user.getUuid(),
                user.getName(),
                user.getUserName()
                );
    }

    public static Card newDtoToEntity(NewCardDTO newCardDTO) {
        return new Card(
                newCardDTO.getQuestion(),
                newCardDTO.getAnswer()
                );
    }

    public static Card existingDtoToEntity(ExistingCardDTO existingCardDTO) {
        return new Card(
                existingCardDTO.getUuid(),
                existingCardDTO.getQuestion(),
                existingCardDTO.getAnswer()
                );
    }

    public static ExistingCardDTO entityToDTO(Card card) {
        return new ExistingCardDTO(
                card.getUuid(),
                card.getQuestion(),
                card.getAnswer()
                );
    }

    public static ExistingUserCardDTO entityToDTO(UserCard userCard) {
        return new ExistingUserCardDTO(
                userCard.getUuid(),
                userCard.getUser().getUuid(),
                userCard.getCard().getUuid(),
                userCard.getBox()
                );
    }

    public static ErrorDTO exceptionToDTO(Exception exception) {
        return new ErrorDTO(exception.getMessage(), true);
    }
}
