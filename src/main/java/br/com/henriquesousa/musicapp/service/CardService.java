package br.com.henriquesousa.musicapp.service;

import java.util.List;

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

}
