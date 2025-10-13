package br.com.henriquesousa.musicapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.service.CardService;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/list")
    public ResponseEntity<List<Card>> list() {
        return ResponseEntity.ok(cardService.list());
    }

}
