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

import br.com.henriquesousa.musicapp.dto.UserCardDTO;
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
    UserRepository userRepository = null;  

    @Autowired
    CardRepository cardRepository = null;  

    @GetMapping
    public ResponseEntity<List<UserCardResponseDTO>> list() {
        List<UserCard> userCards = userCardService.list();
        List<UserCardResponseDTO> responseList = new ArrayList<>();
        for (var userCard : userCards) {
            UserCardResponseDTO responseUserCard = new UserCardResponseDTO(
                    userCard.getUuid(),
                    userCard.getUser().getUuid(),
                    userCard.getCard().getId(),
                    userCard.getBox()
                    );
            responseList.add(responseUserCard);
        }
        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<UserCardResponseDTO> create(@RequestBody UserCardDTO newUserCard) {
        // TODO: precisa mesmo criar tudo isso? o jackson fazia isso automaticamente

        UserCard userCard = new UserCard();

        Optional<User> optionalUser = userRepository.findByUuid(newUserCard.getUserUuid());
        Optional<Card> optionalCard = cardRepository.findById(newUserCard.getCardId());

        User user = null;
        Card card = null;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            // TODO: retornar usuario nao achado
        }

        if (optionalCard.isPresent()) {
            card = optionalCard.get();
        } else {
            // TODO: retornar card nao achado
        }

        userCard.setUser(user);
        userCard.setCard(card); 
        userCard.setBox(newUserCard.getBox());

        Optional<UserCard> optionalUserCardCreated = userCardService.create(userCard);

        if (optionalUserCardCreated.isPresent()) {
            UserCard userCardCreated = optionalUserCardCreated.get(); 
            UserCardResponseDTO response = new UserCardResponseDTO(
                    userCardCreated.getUuid(),
                    user.getUuid(),
                    card.getId(),
                    userCardCreated.getBox()
                    );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
