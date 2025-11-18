package br.com.henriquesousa.musicapp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.repository.UserRepository;
import br.com.henriquesousa.musicapp.service.exception.UserNotCreatedException;
import br.com.henriquesousa.musicapp.service.exception.UserNotFoundException;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository = null;

    public List<User> list() {
        return userRepository.findAll();
    }

    public User getByUuid(UUID uuid) throws UserNotFoundException {
        Optional<User> maybeUser = userRepository.findByUuid(uuid);
        // TODO: inverter? if not present throw, "else" return
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        } 
        throw new UserNotFoundException();
    }

    public void create(User newUser) throws UserNotCreatedException {
        if (userRepository.findByUserName(newUser.getUserName()).isEmpty()) {
            // TODO: testar se tem name
            // TODO: colocar esse teste no controller com @Valid
            if (newUser.getUserName() != null) {
                newUser.setUuid(UUID.randomUUID());
                newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                userRepository.saveAndFlush(newUser);
                LOGGER.info("new user created with UUID: " + newUser.getUuid());
                return;
            }
            // TODO: no momento, se o json nao tiver os campos corretos,
            // retorna empty o que faz com que o controller retorne CONFLICT
            // mas sera que eh o melhor status code pra isso?
            throw new UserNotCreatedException();
        }
        throw new UserNotCreatedException();
    }

    public void update(User updatedUser) throws UserNotFoundException {
        Optional<User> maybeUser = userRepository.findByUserName(updatedUser.getUserName());
        // TODO: permitir que se mude o user_name? talvez seja melhor nao
        // TODO: criar updatedAt?
        // TODO: trocar pra update by uuid
        if (maybeUser.isPresent()) {
            User dbUser = maybeUser.get();
            dbUser.setName(updatedUser.getName());
            userRepository.saveAndFlush(dbUser); 
            return;
        }
        throw new UserNotFoundException();
    }

    public void delete(User userToDelete) throws UserNotFoundException {
        // TODO: try catch?
        Optional<User> maybeUser = userRepository.findByUserName(userToDelete.getUserName());
        // TODO: nao seria melhor usar try aqui e throw dentro de catch?
        // .get() throws NoSuchElementException - if there is no value present
        if (maybeUser.isPresent()) {
            User dbUser = maybeUser.get();
            // TODO: ou um try aqui?
            // exception: erro do banco de dados?
            // só se eu realmente quiser passar essas informacoes detalhadas de erro pro usuario
            userRepository.delete(dbUser);
            return;
        }
        throw new UserNotFoundException();
    }
}
