package br.com.henriquesousa.musicapp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.UserCard;
import br.com.henriquesousa.musicapp.repository.UserCardRepository;

@Service
public class UserCardService {

    @Autowired
    private UserCardRepository userCardRepository = null;

    public List<UserCard> list() {
        return userCardRepository.findAll();
    }

    public Optional<UserCard> create(UserCard newUserCard) {
        // TODO: testar se já tem um usercard com esse usuario e esse card
        newUserCard.setUuid(UUID.randomUUID());
        newUserCard.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        try {
            userCardRepository.saveAndFlush(newUserCard);
            return Optional.of(newUserCard);
        } catch (Exception e)  {
            return Optional.empty();
        }
    }
}
