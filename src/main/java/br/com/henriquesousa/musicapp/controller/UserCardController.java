package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquesousa.musicapp.dto.FactoryDTO;
import br.com.henriquesousa.musicapp.dto.UserCardRequestDTO;
import br.com.henriquesousa.musicapp.dto.UserCardResponseDTO;
import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.entity.UserCard;
import br.com.henriquesousa.musicapp.service.CardService;
import br.com.henriquesousa.musicapp.service.UserCardService;
import br.com.henriquesousa.musicapp.service.UserService;

@RestController
@RequestMapping("/user-cards")
public class UserCardController {

    @Autowired
    private UserCardService userCardService;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserCardResponseDTO>> list() {
        List<UserCard> userCards = userCardService.list();
        List<UserCardResponseDTO> userCardResponses = new ArrayList<>();
        for (var userCard : userCards) {
            UserCardResponseDTO userCardResponse = FactoryDTO.entityToDTO(userCard);
            userCardResponses.add(userCardResponse);
        }
        return ResponseEntity.ok(userCardResponses);
    }

    @PostMapping
    public ResponseEntity<UserCardResponseDTO> create(@RequestBody UserCardRequestDTO newUserCardRequest) {
        // TODO: precisa mesmo criar tudo isso? o jackson fazia isso automaticamente

        User user = userService.getByUuid(newUserCardRequest.getUserUuid());
        Card card = cardService.getByUuid(newUserCardRequest.getCardUuid());

        UserCard userCard = new UserCard(user, card, newUserCardRequest.getBox());

        userCardService.create(userCard);

        UserCardResponseDTO userResponse = FactoryDTO.entityToDTO(userCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
