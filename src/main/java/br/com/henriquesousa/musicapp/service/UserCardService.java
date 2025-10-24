package br.com.henriquesousa.musicapp.service;

import java.sql.Timestamp;
import java.util.List;
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

    public boolean create(UserCard newUserCard) {
        // TODO: testar se já tem um usercard com esse usuario e esse card
        try {
            newUserCard.setUuid(UUID.randomUUID());
            newUserCard.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            userCardRepository.saveAndFlush(newUserCard);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
