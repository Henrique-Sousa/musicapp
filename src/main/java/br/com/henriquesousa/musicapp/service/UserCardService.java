package br.com.henriquesousa.musicapp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.UserCard;
import br.com.henriquesousa.musicapp.repository.UserCardRepository;
import br.com.henriquesousa.musicapp.service.exception.UserCardNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.UserCardNotFoundException;

@Service
public class UserCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardService.class);

    @Autowired
    private UserCardRepository userCardRepository = null;

    public List<UserCard> list() {
        return userCardRepository.findAll();
    }

    // TODO: aparentemente essa funcao nao esta sendo usada
    public UserCard getByUuid(UUID uuid) throws UserCardNotFoundException {
        Optional<UserCard> maybeUserCard = userCardRepository.findByUuid(uuid);
        if (maybeUserCard.isPresent()) {
            return maybeUserCard.get();
        }
        throw new UserCardNotFoundException();
    }

    public void create(UserCard newUserCard) throws UserCardNotCreatedException {
        // TODO: testar se já tem um usercard com esse usuario e esse card
        try {
            newUserCard.setUuid(UUID.randomUUID());
            newUserCard.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            userCardRepository.saveAndFlush(newUserCard);
        } catch (Exception e) {
            throw new UserCardNotCreatedException();
        }
    }

    public void update(UserCard updatedUserCard) throws UserCardNotFoundException {
        // TODO: impedir de fazer alteracoes que tornam esse userCard igual a outro? 
        Optional<UserCard> maybeUserCard = userCardRepository.findByUuid(updatedUserCard.getUuid());
        if (maybeUserCard.isPresent()) {
            UserCard dbUserCard = maybeUserCard.get();
            dbUserCard.setUser(updatedUserCard.getUser());
            dbUserCard.setCard(updatedUserCard.getCard());
            dbUserCard.setBox(updatedUserCard.getBox());
            userCardRepository.saveAndFlush(dbUserCard); 
            updatedUserCard.setUuid(dbUserCard.getUuid());
            return;
        }
        throw new UserCardNotFoundException();
    }

    public void delete(UserCard userCardToDelete) throws UserCardNotFoundException {
        Optional<UserCard> maybeUserCard = userCardRepository.findByUuid(userCardToDelete.getUuid());
        if (maybeUserCard.isPresent()) {
            UserCard dbUserCard = maybeUserCard.get();
            userCardRepository.delete(dbUserCard);
            userCardToDelete.setUser(dbUserCard.getUser());
            userCardToDelete.setCard(dbUserCard.getCard());
            userCardToDelete.setBox(dbUserCard.getBox());
            return;
        }
        throw new UserCardNotFoundException();
    }
}
