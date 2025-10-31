package br.com.henriquesousa.musicapp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.repository.UserRepository;
import br.com.henriquesousa.musicapp.service.exception.UserNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.UserNotFoundException;

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

    public void create(User newUser) throws UserNotCreatedException {
        // TODO: try catch
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
            return;
        }
        throw new UserNotCreatedException();
    }

    public void update(User updatedUser) throws UserNotFoundException {
        Optional<User> maybeUser = userRepository.findByUserName(updatedUser.getUserName());
        // TODO: permitir que se mude o user_name? talvez seja melhor nao
        // TODO: criar updatedAt?
        if (maybeUser.isPresent()) {
            User dbUser = maybeUser.get();
            dbUser.setName(updatedUser.getName());
            userRepository.saveAndFlush(dbUser); 
            // TODO: por que updatedUser = dbUser nao eh o suficiente?
            updatedUser.setName(dbUser.getName());
            updatedUser.setUuid(dbUser.getUuid());
            return;
        }
        throw new UserNotFoundException();
    }

    public void delete(User userToDelete) throws UserNotFoundException {
        // TODO: try catch?
        Optional<User> maybeUser = userRepository.findByUserName(userToDelete.getUserName());
        // TODO: nao seria melhor usar try aqui e throw dentro de catch?
        if (maybeUser.isPresent()) {
            // TODO: ou um try aqui?
            // exception: erro do banco de dados?
            userRepository.delete(userToDelete);
        }
        throw new UserNotFoundException();
    }
}
