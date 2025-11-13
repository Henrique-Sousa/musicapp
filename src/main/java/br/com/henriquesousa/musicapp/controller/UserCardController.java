package br.com.henriquesousa.musicapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquesousa.musicapp.dto.ErrorDTO;
import br.com.henriquesousa.musicapp.dto.ExistingUserCardDTO;
import br.com.henriquesousa.musicapp.dto.FactoryDTO;
import br.com.henriquesousa.musicapp.dto.NewUserCardDTO;
import br.com.henriquesousa.musicapp.dto.SuccessDTO;
import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.entity.UserCard;
import br.com.henriquesousa.musicapp.service.CardService;
import br.com.henriquesousa.musicapp.service.UserCardService;
import br.com.henriquesousa.musicapp.service.UserService;
import br.com.henriquesousa.musicapp.service.exception.CardNotFoundException;
import br.com.henriquesousa.musicapp.service.exception.UserCardNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.UserCardNotFoundException;
import br.com.henriquesousa.musicapp.service.exception.UserNotFoundException;

@RestController
@RequestMapping("/user-cards")
public class UserCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardService.class);

    @Autowired
    private UserCardService userCardService;

    @GetMapping
    public ResponseEntity<List<ExistingUserCardDTO>> list() {
        List<UserCard> userCards = userCardService.list();
        List<ExistingUserCardDTO> userCardResponses = new ArrayList<>();
        for (var userCard : userCards) {
            ExistingUserCardDTO userCardResponse = FactoryDTO.entityToDTO(userCard);
            userCardResponses.add(userCardResponse);
        }
        return ResponseEntity.ok(userCardResponses);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewUserCardDTO newUserCardRequest) {
        try {
            User user = new User();
            user.setUuid(newUserCardRequest.getUserUuid());
            Card card = new Card(); 
            card.setUuid(newUserCardRequest.getCardUuid());
            UserCard userCard = new UserCard(user, card, newUserCardRequest.getBox());
            userCardService.create(userCard);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDTO(true));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (UserCardNotCreatedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ExistingUserCardDTO updateUserCardRequest) {
        // TODO: tenho que "hydrate" o objeto userCard mesmo? só preciso das uuid
        // do user e do card pra fazer essa operação
        try {
            User user = new User(); 
            user.setUuid(updateUserCardRequest.getUserUuid());
            Card card = new Card(); 
            card.setUuid(updateUserCardRequest.getCardUuid());
            UUID uuid = updateUserCardRequest.getUuid();
            UserCard userCard = new UserCard(uuid, user, card, updateUserCardRequest.getBox());
            userCardService.update(userCard);
            return ResponseEntity.ok(new SuccessDTO(true));
        } catch (UserCardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid) {
        UserCard userCard = new UserCard();
        userCard.setUuid(uuid);
        try {
            userCardService.delete(userCard);
            return ResponseEntity.ok(new SuccessDTO(true));
        } catch (UserCardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }
}
