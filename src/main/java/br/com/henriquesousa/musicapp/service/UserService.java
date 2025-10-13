package br.com.henriquesousa.musicapp.service;

import java.util.List;
import java.util.Optional;

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

    // TODO: fazer try/catch no lugar de if's?

    public Optional<User> create(User newUser) {
        if (userRepository.findByUserName(newUser.getUserName()).isEmpty()) {
            // TODO: usar @Valid?
            // TODO: refatorar - testar primeiro se ja NAO existe o usuario
            // AND se o json NAO tem os fields corretos
            // e caso algum desses de fato falhe, retornar .empty
            // depois, fora do if, salvar e retornar usuario
            
            // TODO: testar se tem name
            
            if (newUser.getUserName() != null) {
                userRepository.saveAndFlush(newUser);
                return Optional.of(newUser);
            }
            // TODO: no momento, se o json nao tiver os campos corretos,
            // retorna empty o que faz com que o controller retorne CONFLICT
            // mas sera que eh o melhor status code pra isso?
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<User> update(User updatedUser) {
        Optional<User> user = userRepository.findByUserName(updatedUser.getUserName());
        // TODO: permitir que se mude o user_name? talvez seja melhor nao
        if (user.isPresent()) {
            user.get().setName(updatedUser.getName());
            userRepository.saveAndFlush(user.get()); 
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> delete(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            userRepository.deleteByUserName(userName);
            return user;
        }
        return Optional.empty();
    }
}
