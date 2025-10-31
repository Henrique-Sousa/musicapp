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

import br.com.henriquesousa.musicapp.dto.CardRequestDTO;
import br.com.henriquesousa.musicapp.dto.CardResponseDTO;
import br.com.henriquesousa.musicapp.dto.FactoryDTO;
import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.service.CardService;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> list() {
        List<Card> cards = cardService.list(); 
        List<CardResponseDTO> cardResponses = new ArrayList<>();
        for (var card : cards) {
            CardResponseDTO cardResponse = FactoryDTO.entityToDTO(card);
            cardResponses.add(cardResponse);
        }
        return ResponseEntity.ok(cardResponses);
    }

    @PostMapping
    public ResponseEntity<CardResponseDTO> create(@RequestBody CardRequestDTO newCardRequest) {
        Card card = FactoryDTO.dtoToEntity(newCardRequest);
        try {
            cardService.create(card);
            return ResponseEntity.status(HttpStatus.CREATED).body(FactoryDTO.entityToDTO(card));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(FactoryDTO.entityToDTO(card));
        }
    }
}
