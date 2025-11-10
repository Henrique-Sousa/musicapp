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
import br.com.henriquesousa.musicapp.dto.ExistingCardDTO;
import br.com.henriquesousa.musicapp.dto.FactoryDTO;
import br.com.henriquesousa.musicapp.dto.NewCardDTO;
import br.com.henriquesousa.musicapp.dto.SuccessDTO;
import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.service.CardService;
import br.com.henriquesousa.musicapp.service.exception.CardNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.CardNotFoundException;

@RestController
@RequestMapping("/cards")
public class CardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<List<ExistingCardDTO>> list() {
        List<Card> cards = cardService.list(); 
        List<ExistingCardDTO> cardResponses = new ArrayList<>();
        for (var card : cards) {
            ExistingCardDTO cardResponse = FactoryDTO.entityToDTO(card);
            cardResponses.add(cardResponse);
        }
        return ResponseEntity.ok(cardResponses);
    }

    // TODO: é igualzinho ao create do User, devo refatorar?
    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewCardDTO newCardRequest) {
        Card card = FactoryDTO.newDtoToEntity(newCardRequest);
        try {
            cardService.create(card);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDTO(true));
        } catch (CardNotCreatedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ExistingCardDTO updateCardRequest) {
        Card card = FactoryDTO.existingDtoToEntity(updateCardRequest);
        try {
            cardService.update(card);
            return ResponseEntity.ok(new SuccessDTO(true));
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid) {
        Card card = new Card();
        card.setUuid(uuid);
        try {
            cardService.delete(card);
            return ResponseEntity.ok(new SuccessDTO(true));
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FactoryDTO.exceptionToDTO(e));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("error", true));
        }
    }
}
