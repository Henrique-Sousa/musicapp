package br.com.henriquesousa.musicapp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository = null;

    public List<User> list() {
        return userRepository.findAll();
    }

    public User getByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid).get();
    }

    public boolean create(User newUser) {
        if (userRepository.findByUserName(newUser.getUserName()).isEmpty()) {
            // TODO: usar @Valid?
            // TODO: refatorar - testar primeiro se ja NAO existe o usuario
            // AND se o json NAO tem os fields corretos
            // e caso algum desses de fato falhe, retornar .empty
            // depois, fora do if, salvar e retornar usuario
            
            // TODO: testar se tem name
            
            // TODO: colocar esse teste no controller?
            if (newUser.getUserName() != null) {
                newUser.setUuid(UUID.randomUUID());
                newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                userRepository.saveAndFlush(newUser);
            }
            // TODO: no momento, se o json nao tiver os campos corretos,
            // retorna empty o que faz com que o controller retorne CONFLICT
            // mas sera que eh o melhor status code pra isso?
            return true;
        }
        return false;

    }

    public boolean update(User updatedUser) {
        Optional<User> user = userRepository.findByUserName(updatedUser.getUserName());
        // TODO: permitir que se mude o user_name? talvez seja melhor nao
        // TODO: criar updatedAt?
        if (user.isPresent()) {
            user.get().setName(updatedUser.getName());
            userRepository.saveAndFlush(user.get()); 
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(User userToDelete) {
        Optional<User> maybeUser = userRepository.findByUserName(userToDelete.getName());
        if (maybeUser.isPresent()) {
            userRepository.delete(userToDelete);
            return true;
        }
        return false;
    }
}
