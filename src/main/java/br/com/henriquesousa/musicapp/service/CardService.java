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

    public Card getByUuid(UUID uuid) {
        return cardRepository.findByUuid(uuid).get();
    }

    // TODO: deveria retornar um objeto com boolean, Card e errorMessage?
    public boolean create(Card newCard) throws Exception {
        // TODO: testar se ja tem um card igual
        try {
            newCard.setUuid(UUID.randomUUID());
            newCard.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            cardRepository.saveAndFlush(newCard);
            return true;
        } catch (Exception e) {
        //    return false;
            throw new Exception();
        }
    }
}
