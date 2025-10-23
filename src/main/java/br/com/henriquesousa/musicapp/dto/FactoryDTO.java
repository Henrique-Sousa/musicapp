package br.com.henriquesousa.musicapp.dto;

import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.entity.UserCard;

public class FactoryDTO {
    public static UserResponseDTO entityToDTO(User user) {
        return new UserResponseDTO(
                user.getUuid(),
                user.getName(),
                user.getUserName()
        );
    }

    public static User dtoToEntity(UserRequestDTO userRequestDTO) {
        return new User(
                userRequestDTO.getName(),
                userRequestDTO.getUserName()
        );
    }

    public static UserCardResponseDTO entityToDTO(UserCard userCard) {
        return new UserCardResponseDTO(
                    userCard.getUuid(),
                    userCard.getUser().getUuid(),
                    userCard.getCard().getId(),
                    userCard.getBox()
        );
    }
}
