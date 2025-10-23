package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import br.com.henriquesousa.musicapp.repository.CardRepository;
import br.com.henriquesousa.musicapp.repository.UserRepository;
import br.com.henriquesousa.musicapp.service.UserCardService;

@RestController
@RequestMapping("/user-cards")
public class UserCardController {

    @Autowired
    private UserCardService userCardService;

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private CardRepository cardRepository;  

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

        // TODO: jogar essa logica pro service
        UserCard userCard = new UserCard();

        Optional<User> maybeUser = userRepository.findByUuid(newUserCardRequest.getUserUuid());
        Optional<Card> maybeCard = cardRepository.findById(newUserCardRequest.getCardId());

        User user = null;
        Card card = null;

        if (maybeUser.isPresent()) {
            user = maybeUser.get();
        } else {
            // TODO: retornar usuario nao achado
        }

        if (maybeCard.isPresent()) {
            card = maybeCard.get();
        } else {
            // TODO: retornar card nao achado
        }

        userCard.setUser(user);
        userCard.setCard(card); 
        userCard.setBox(newUserCardRequest.getBox());

        Optional<UserCard> maybeSavedUserCard = userCardService.create(userCard);

        if (maybeSavedUserCard.isPresent()) {
            UserCardResponseDTO userResponse = FactoryDTO.entityToDTO(userCard);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
