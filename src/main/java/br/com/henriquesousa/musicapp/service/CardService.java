package br.com.henriquesousa.musicapp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.Card;
import br.com.henriquesousa.musicapp.repository.CardRepository;
import br.com.henriquesousa.musicapp.service.exception.CardNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.CardNotFoundException;

@Service
public class CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardService.class);

    @Autowired
    private CardRepository cardRepository = null;

    public List<Card> list() {
        return cardRepository.findAll();
    }

    public Card getByUuid(UUID uuid) throws CardNotFoundException {
        Optional<Card> maybeCard = cardRepository.findByUuid(uuid);
        if (maybeCard.isPresent()) {
            return maybeCard.get();
        }
        throw new CardNotFoundException();
    }

    // TODO: deveria retornar um objeto com boolean, Card e errorMessage?
    public void create(Card newCard) throws CardNotCreatedException {
        // TODO: testar se ja tem um card igual
        // é por isso que estou fazendo um try catch aqui no lugar de if
        // porque ainda nao estou testando se tem um card igual
        LOGGER.debug("tentando criar o card");
        try {
            newCard.setUuid(UUID.randomUUID());
            newCard.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            LOGGER.trace("criando card com uuid " + newCard.getUuid());
            cardRepository.saveAndFlush(newCard);
            LOGGER.info("card criado com sucesso com o id: " + newCard.getId());
        } catch (Exception e) {
            throw new CardNotCreatedException();
        }
    }

    public void update(Card updatedCard) throws CardNotFoundException {
        // TODO: impedir de fazer alteracoes que tornam esse card igual a outro? 
        Optional<Card> maybeCard = cardRepository.findByUuid(updatedCard.getUuid());
        if (maybeCard.isPresent()) {
            Card dbCard = maybeCard.get();
            dbCard.setQuestion(updatedCard.getQuestion());
            dbCard.setAnswer(updatedCard.getAnswer());
            cardRepository.saveAndFlush(dbCard); 
            updatedCard.setUuid(dbCard.getUuid());
            return;
        }
        throw new CardNotFoundException();
    }

    public void delete(Card cardToDelete) throws CardNotFoundException {
        Optional<Card> maybeCard = cardRepository.findByUuid(cardToDelete.getUuid());
        if (maybeCard.isPresent()) {
            Card dbCard = maybeCard.get();
            cardRepository.delete(dbCard);
            cardToDelete.setQuestion(dbCard.getQuestion());
            cardToDelete.setAnswer(dbCard.getAnswer());
            return;
        }
        throw new CardNotFoundException();
    }
}
