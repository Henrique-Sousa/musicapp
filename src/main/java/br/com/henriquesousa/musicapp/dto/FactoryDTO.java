package br.com.henriquesousa.musicapp.dto;

import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.entity.UserCard;

public class FactoryDTO {
    public static User dtoToEntity(UserRequestDTO userRequestDTO) {
        return new User(
                userRequestDTO.getName(),
                userRequestDTO.getUserName()
                );
    }

    public static UserResponseDTO entityToDTO(User user) {
        return new UserResponseDTO(
                user.getUuid(),
                user.getName(),
                user.getUserName()
                );
    }

    public static UserCardResponseDTO entityToDTO(UserCard userCard) {
        return new UserCardResponseDTO(
                userCard.getUuid(),
                userCard.getUser().getUuid(),
                userCard.getCard().getUuid(),
                userCard.getBox()
                );
    }

    public static Card dtoToEntity(CardRequestDTO cardRequestDTO) {
        return new Card(
                cardRequestDTO.getQuestion(),
                cardRequestDTO.getAnswer()
                );
    }

    public static CardResponseDTO entityToDTO(Card card) {
        return new CardResponseDTO(
                card.getUuid(),
                card.getQuestion(),
                card.getAnswer()
                );
    }

    public static ErrorDTO exceptionToDTO(Exception exception) {
        return new ErrorDTO(exception.getMessage(), true);
    }
}
