package br.com.henriquesousa.musicapp.service;

import java.util.List;

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

}
