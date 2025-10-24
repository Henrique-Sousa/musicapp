package br.com.henriquesousa.musicapp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.repository.CardRepository;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository = null;

    public List<Card> list() {
        return cardRepository.findAll();
    }

    public void create(Card newCard) {
        // TODO: testar se ja tem um card igual
        newCard.setUuid(UUID.randomUUID());
        newCard.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        cardRepository.saveAndFlush(newCard);
    }
}
