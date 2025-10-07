package br.com.henriquesousa.musicapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.repository.UserRepository;

@Service
public class UserService {

    private List<User> users = new ArrayList<>(); 

    @Autowired
    private UserRepository userRepository = null;

    public UserService() {

        User joao = new User(1l, "Joao");
        User maria = new User(2l, "Maria");

        users.add(joao);
        users.add(maria);
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public Optional<User> create(User newUser) {
        // TODO: testar se existe um mesmo usuario pelo cpf ou email
        // TODO: criar findByName e findByUserName e testar se existe usuario
        // por username, no lugar de cpf e email
        if (userRepository.findByName(newUser.getName()).isEmpty()) {
            userRepository.saveAndFlush(newUser);
            return Optional.of(newUser);
        }
        return Optional.empty();
    }

    public Optional<User> update(User updatedUser) {
        Optional<User> user = userRepository.findById(updatedUser.getId());
        if (user.isPresent()) {
            user.get().setName(updatedUser.getName());
            userRepository.saveAndFlush(user.get()); 
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return user;
        }
        return Optional.empty();
    }
}
